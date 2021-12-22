package com.example.chapter01;

/**
 * 守护进程
 */
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
