package com.lovehins.generator.thread;

import com.lovehins.generator.bean.Person;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by lovedrose on 11/04/2018.
 */
public class MyRunner implements Runnable {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (person.getAge() < 100) {
            person.grow();
        }

    }
}
