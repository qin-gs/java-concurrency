package com.example.chapter02;

/**
 * volatile 可以保证
 * 写之前的操作不会被编译器重排序到写之后
 * 读之后的操作不会被重排序到读之前
 */
public class TestReorder {

    public static int num = 0;
    public static boolean ready = false;

    public static class ReadThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (ready) {
                    System.out.println(num + num);
                }
                System.out.println("read thread...");
            }
        }
    }

    public static class WriteThread extends Thread {
        @Override
        public void run() {
            num = 2;
            ready = true;
            System.out.println("write thread set over...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadThread read = new ReadThread();
        read.start();

        WriteThread write = new WriteThread();
        write.start();

        Thread.sleep(10);
        read.interrupt();
        System.out.println("main exit");
    }
}
