package com.lovehins.generator.thread;

import java.util.concurrent.Exchanger;

/**
 * Created by lovedrose on 11/04/2018.
 */
public class MyExchanger implements Runnable {

    private Exchanger<String> exchange;
    private String name;


    @Override
    public void run() {
        try {
            System.out.println("thread is " + name + " : " + exchange.exchange(name));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Exchanger<String> getExchange() {
        return exchange;
    }

    public void setExchange(Exchanger<String> exchange) {
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyExchanger(Exchanger<String> exchange, String name) {
        this.exchange = exchange;
        this.name = name;
    }

    public MyExchanger() {
    }
}
