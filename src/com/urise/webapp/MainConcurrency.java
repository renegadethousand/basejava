package com.urise.webapp;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {

    public static final int THREADS_NUMBER = 10_000;
    private volatile int counter;

    private static final Object LOCK = new Object();

    private static final Object BANK_ACCOUNT_1 = new Object();
    private static final Object BANK_ACCOUNT_2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        deadLock();
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

    private static void deadLock() {
        new Thread(() -> {
            transferMoney(BANK_ACCOUNT_1, BANK_ACCOUNT_2);
        }).start();
        new Thread(() -> {
            transferMoney(BANK_ACCOUNT_2, BANK_ACCOUNT_1);
        }).start();
    }

    private static void transferMoney(Object bankAccountFrom, Object bankAccountTo) {
        synchronized (bankAccountFrom) {
            System.out.println("Захвачен монитор обьекта " + bankAccountFrom + " потоком " + Thread.currentThread().getName());
            synchronized (bankAccountTo) {
                System.out.println("Захвачен монитор обьекта " + bankAccountTo + " потоком " + Thread.currentThread().getName());
            }
            System.out.println("Освобожден монитор обьекта " + bankAccountTo + " потоком " + Thread.currentThread().getName());
        }
        System.out.println("Освобожден монитор обьекта " + bankAccountFrom + " потоком " + Thread.currentThread().getName());
    }
}
