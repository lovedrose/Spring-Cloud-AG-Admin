package com.lovehins.generator.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by lovedrose on 11/04/2018.
 */
public class MyCyclicBarrier implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private String threadName;

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println("Thread " + threadName + "'ll wait..");
            cyclicBarrier.await();
            System.out.println("[Thread " + threadName + " is over.]");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public MyCyclicBarrier(String threadName, CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
        this.threadName = threadName;
    }

    public MyCyclicBarrier() {
    }
}
