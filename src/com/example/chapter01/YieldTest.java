package com.example.chapter01;

public class YieldTest implements Runnable {
    public YieldTest() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            if (i % 5 == 0) {
                System.out.println(Thread.currentThread() + " yield...");
                // 加上 yield 方法会让这两个输出分开
                Thread.yield();
            }
        }
        System.out.println("over");
    }

    public static void main(String[] args) {
        new YieldTest();
        new YieldTest();
        new YieldTest();
    }
}
