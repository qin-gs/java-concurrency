package com.example.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    private static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(() -> {
            System.out.println(Thread.currentThread() + " over");
            semaphore.release();
        });

        service.submit(() -> {
            System.out.println(Thread.currentThread() + " over");
            semaphore.release();
        });
        semaphore.acquire(2);
        System.out.println("all child thread over");
        service.shutdown();
    }
}
