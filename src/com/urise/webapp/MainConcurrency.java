package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {

    public static final int THREADS_NUMBER = 10_000;
    private volatile int counter;
    private final AtomicInteger atomicInteger = new AtomicInteger();

    private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> simpleDateFormat = new ThreadLocal<>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

    public static void main(String[] args) throws InterruptedException, ExecutionException {
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
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("Processor cores: " + Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService completionService = new ExecutorCompletionService(executorService);

//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(simpleDateFormat.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
//            while (!future.isDone()) {
//                Thread.sleep(100);
//            }
//            Future poll = completionService.poll();
//            System.out.println(future.get());
//            Thread thread1 = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//                latch.countDown();
//            });
//            thread1.start();
//            threads.add(thread);
        }

//        for (Thread thread1 : threads) {
//            thread1.join();
//        }
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(mainConcurrency.atomicInteger.get());
    }

    private void inc() {
        atomicInteger.incrementAndGet();
//        lock.lock();
//        try {
//            counter++;
//        } finally {
//            lock.unlock();
//        }
    }
}
