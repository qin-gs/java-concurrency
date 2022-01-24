package com.example.chapter11;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {
    /**
     * 核心线程，最大线程，队列长度 都是 1
     */
    private final static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L,
            TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future futureOne = executor.submit(() -> {
            System.out.println("start runnable one");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Future futureTwo = executor.submit(() -> System.out.println("start runnable two"));

        // 添加两个任务之后，第三个会被拒绝
        Future futureThree = null;
        try {
            futureThree = executor.submit(() -> System.out.println("start runnable three"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        System.out.println("task one " + futureOne.get());
        System.out.println("task two " + futureTwo.get());
        System.out.println("task three " + (futureThree == null ? null : futureThree.get()));

        executor.shutdown();

    }
}
