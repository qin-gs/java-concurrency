package com.qin.chapter01;

public class DaemonTest {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (; ; ) {
            }
        });

        thread.setDaemon(true);
        thread.start();

        System.out.println("main tread is over");
    }
}
