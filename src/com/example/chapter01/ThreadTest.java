package com.example.chapter01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的三种方法
 */
public class ThreadTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 继承Thread
        MyThread thread = new MyThread();
        thread.start();
        // 实现Runnable接口
        RunnableTask task = new RunnableTask();
        new Thread(task).start();
        // 实现callable接口
        FutureTask<Integer> futureTask = new FutureTask<>(new CallableTask());
        new Thread(futureTask).start();
        futureTask.get();
        System.out.println("a father thread");
    }

    /**
     * 继承Thread类
     */
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("a child thread extends Thread");
        }
    }

    /**
     * 实现Runnable接口
     */
    public static class RunnableTask implements Runnable {
        @Override
        public void run() {
            System.out.println("a child thread implements Runnable");
        }
    }

    /**
     * 实现callable接口
     */
    public static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() {
            System.out.println("a child thread implement callable");
            return 1;
        }
    }
}
