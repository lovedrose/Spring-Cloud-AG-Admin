package com.lovehins.generator.thread;

import com.lovehins.generator.bean.Person;

/**
 * Created by lovedrose on 12/04/2018.
 */
public class PersonThreadLocal implements Runnable {

    private ThreadLocal<Person> personThreadLocal = new ThreadLocal<>();
    private String name;
    private Person person;


    @Override
    public void run() {
        person.setAge((int) Thread.currentThread().getId());
        personThreadLocal.set(person);
        System.out.println(name + ":" + personThreadLocal.get().getAge());
    }


    public PersonThreadLocal(String name, Person person) {
        this.name = name;
        this.person = person;
    }
}
