package com.lovehins.generator.bean;

/**
 * Created by lovedrose on 11/04/2018.
 */
public class Person {
    private int age = 0;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public synchronized void grow() {
        this.notifyAll();
        if (this.age < 100) {
            this.age += 1;
            System.out.println(Thread.currentThread().getId() + " -- " + this.age);
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("我已经100了啊？");
        }
    }

    public Person(int age) {
        this.age = age;
    }

    public Person() {
    }
}
