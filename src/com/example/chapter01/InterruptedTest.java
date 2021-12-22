package com.example.chapter01;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * isInterrupted 根据中断标志判断线程是否终止
 * interrupted() 内部是获取当前线程的中断状态，并且会清除中断标志
 */
public class InterruptedTest {
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {

                }
            }
        });
        threadA.start();

        // 设置中断标志
        threadA.interrupt();

        // 获取中断标志
        System.out.println("threadA: " + threadA.isInterrupted());
        // 获取中断标志并重置
        // Thread.interrupted() 内部是获取当前线程的中断状态，threadA.interrupted() 也是获取当前线程(main)的状态 并且会清除中断标志
        // main 线程没有被中断，这里是 false
        System.out.println("threadA: " + threadA.interrupted());
        // 获取中断标志并重置
        System.out.println("threadA: " + Thread.interrupted());
        // 获取中断标志
        System.out.println("threadA: " + threadA.isInterrupted());

        threadA.join();
        System.out.println("main over");
    }

    /**
     * interrupt 方法可以使线程在 sleep 指定的时间之前直接返回
     */
    public static void interruptTest() throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("threadA sleep");
                    // 该线程会在 sleep 状态下被打断，然后直接返回
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("threadA awake");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("threadA was interrupted");
                }
            }
        });
        threadA.start();
        // 让子线程进行 sleep 状态
        TimeUnit.SECONDS.sleep(1);

        // 打断 threadA 线程的 sleep 状态，让子线程从 sleep 方法返回
        threadA.interrupt();
        // 等待子线程执行完成
        threadA.join();

        System.out.println("main over");
    }

    /**
     * isInterrupted 根据中断标志判断线程是否终止
     */
    public static void isInterruptedTest() throws InterruptedException {
        Thread thread = new Thread(() -> {
            // 根据中断标志判断线程是否终止
            // 如果当前线程被中断，结束循环
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread() + " hello");
            }
        });
        thread.start();

        // 主线程等待一秒后中断子线程
        Thread.sleep(1000);
        System.out.println("main thread interrupt thread");
        thread.interrupt();

        // 等待子线程执行完毕
        thread.join();

        System.out.println("main is over");
    }

    public void run() {

        while (!Thread.currentThread().isInterrupted() && hasMoreWork()) {
            doSomething();
        }

    }

    private boolean hasMoreWork() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private void doSomething() {

    }
}
