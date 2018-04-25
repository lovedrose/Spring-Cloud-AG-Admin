package com.lovehins.generator;

import com.lovehins.generator.bean.Person;
import com.lovehins.generator.thread.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

/**
 * Created by lovedrose on 11/04/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@Slf4j
public class ThreadTest {

    @Test
    public void testExtend() {
        System.out.println("我是主的");
        MyThread t0 = new MyThread();
        t0.start();
    }

    @Test
    public void testRunnable() {
        MyRunner runner0 = new MyRunner();
        Person p = new Person();
        p.setAge(0);
        runner0.setPerson(p);

        Thread t0 = new Thread(runner0);
        Thread t1 = new Thread(runner0);

        t1.start();
        t0.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCallable() {
        FutureTask<Integer> task = new FutureTask<Integer>((Callable<Integer>) ()-> {
            return 5;
        });
        new Thread(task, "有返回值的线程").start();
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCyclicBarrier() {
        CyclicBarrier cb = new CyclicBarrier(4);
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(new MyCyclicBarrier("A", cb));
        es.submit(new MyCyclicBarrier("B", cb));
        es.submit(new MyCyclicBarrier("C", cb));
        es.submit(new MyCyclicBarrier("D", cb));
        es.shutdown();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSemaphore() {
        Semaphore semaphore = new Semaphore(5);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            System.out.println("这里是第" + (i + 1) + "次循环");
            executorService.submit(new MySemaphore("线程" + (i + 1), semaphore));
        }
        executorService.shutdown();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExchanger() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(new MyExchanger(exchanger,"A"));
        service.execute(new MyExchanger(exchanger,"B"));
        Thread.sleep(5000);
        service.shutdown();
    }

    @Test
    public void threadLocal() {

        ThreadLocal<Person> personThreadLocal = new ThreadLocal<>();
        Person person = new Person(99);
        personThreadLocal.set(person);
        System.out.println("main:" + personThreadLocal.get().getAge());


        new Thread(new PersonThreadLocal("Rose", person)).start();
        new Thread(new PersonThreadLocal("Derrick", person)).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main:" + personThreadLocal.get().getAge());
    }
}
