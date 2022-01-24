package com.example.chapter10;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CycleBarrierTest {

    /**
     * 添加一个所有子线程全部到达屏障后执行的任务 (只执行一次)
      */
    private static final CyclicBarrier barrier = new CyclicBarrier(2, () -> {
        System.out.println(Thread.currentThread() + " task 1 merge result");
    });

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " task1");
                System.out.println(Thread.currentThread() + " enter in barrier");
                barrier.await();
                System.out.println(Thread.currentThread() + " enter out barrier");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        service.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " task2");
                System.out.println(Thread.currentThread() + " enter in barrier");
                barrier.await();
                System.out.println(Thread.currentThread() + " enter out barrier");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
    }

}
