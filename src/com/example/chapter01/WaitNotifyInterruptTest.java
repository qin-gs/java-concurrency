package com.example.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * 当一个线程调用共享对象的 wait 方法被阻塞挂起后，如果其他线程中断了改线程，
 * 该线程或抛出 InterruptedException 异常
 */
public class WaitNotifyInterruptTest {
    static final Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("begin -----");
                synchronized (obj) {
                    // 挂起当前线程
                    obj.wait();
                }
                System.out.println("end -----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        // 中断被挂起的线程，会抛出异常
        thread.interrupt();
    }
}
