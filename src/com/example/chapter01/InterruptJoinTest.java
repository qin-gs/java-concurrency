package com.example.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * 线程A调用线程B的 join 方法后会被阻塞，其他线程调用线程A的 interrupt 方法中断线程A时，
 * 线程A会抛出 InterruptedException 异常然后返回
 */
public class InterruptJoinTest {

    public static void main(String[] args) {
        // threadA 一直在运行
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("threadA begin");
                for (;;){

                }
            }
        });

        // 获取主线程
        Thread mainThread = Thread.currentThread();

        // threadB 等待一秒后中断主线程
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 中断主线程
                mainThread.interrupt();
            }
        });

        threadA.start();
        threadB.start();

        try {
            // 主线程等待 threadA 执行完毕
            // 等待过程中被 threadB 中断
            // 导致抛出异常，然后返回
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
