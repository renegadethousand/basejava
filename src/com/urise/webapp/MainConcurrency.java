package com.urise.webapp;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {

    public static final int THREADS_NUMBER = 10_000;
    private volatile int counter;

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
//                throw new IllegalStateException();
            }
        };
        thread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();
        System.out.println(thread.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread1 = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread1.start();
            threads.add(thread);
        }

        for (Thread thread1 : threads) {
            thread1.join();
        }

        System.out.println(mainConcurrency.counter);
    }

    private void inc() {
        counter++;
    }


}
