package com.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();
    private static final Object ANOTHER_LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(mainConcurrency.counter);

        //Deadlock

        // Thread-1
        Runnable block1 = () -> {
            synchronized (LOCK) {
                System.out.println("Thread 1: Holding lock 1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (ANOTHER_LOCK) {
                    System.out.println("Thread 1: Holding lock 1 & 2...");
                }
            }
        };

        // Thread-2
        Runnable block2 = () -> {
            synchronized (ANOTHER_LOCK) {
                System.out.println("Thread 2: Holding lock 2...");
                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (LOCK) {
                    System.out.println("Thread 2: Holding lock 2 & 1...");
                }
            }
        };
        new Thread(block1).start();
        new Thread(block2).start();
    }

    private synchronized void inc() {
        counter++;
    }
}
