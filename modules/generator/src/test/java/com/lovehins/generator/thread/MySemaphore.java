package com.lovehins.generator.thread;

import java.util.concurrent.Semaphore;

/**
 * Created by lovedrose on 11/04/2018.
 */
public class MySemaphore implements Runnable {

    private String threadName;
    private Semaphore semaphore;

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("Thread " + threadName + " 正在保存数据...");
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.release();
        System.out.println("Thread " + threadName + " 释放许可");
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public MySemaphore() {
    }

    public MySemaphore(String threadName, Semaphore semaphore) {
        this.threadName = threadName;
        this.semaphore = semaphore;
    }
}
