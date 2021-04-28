package com.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
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

        //deadlock

        String lock_1 = "first lock";
        String lock_2 = "second lock";

        Thread thread_1 = new Thread(new DeadlockRunnable("First Thread", lock_1, lock_2));
        Thread thread_2 = new Thread(new DeadlockRunnable("Second Thread", lock_2, lock_1));

        thread_1.start();
        thread_2.start();
    }

    private synchronized void inc() {
        counter++;
    }

    static class DeadlockRunnable implements Runnable {
        String name;
        final Object firstLock;
        final Object secondLock;

        DeadlockRunnable(String name, String firstLock, String secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (firstLock) {
                System.out.println(name + " is holding " + firstLock);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " is waiting for " + secondLock);
                synchronized (secondLock) {
                    System.out.println(name + "is holding " + firstLock + " " + secondLock);
                }
            }
        }
    }
}
