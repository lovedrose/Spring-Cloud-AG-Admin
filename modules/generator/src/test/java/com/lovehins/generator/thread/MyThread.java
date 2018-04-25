package com.lovehins.generator.thread;

/**
 * Created by lovedrose on 11/04/2018.
 */
public class MyThread extends Thread {

    private static int a = 0;

    @Override
    public void run() {
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        while (a < 50) {
            a += 1;
            System.out.println("我是子线程" + Thread.currentThread().getId() + ",现在a是" + a);
        }
    }
}
