package com.qin.chapter01;

public class InterruptedTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread() + " hello");
            }
        });
        thread.start();
        Thread.sleep(1000);

        System.out.println("main thread interrupt thread");
        thread.interrupt();

        thread.join();

        System.out.println("main is over");
    }
}
