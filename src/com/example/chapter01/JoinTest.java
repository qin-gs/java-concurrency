package com.example.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * Thread#join 方法，等待线程执行终止
 */
public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadA over");
            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadB over");
            }
        });

        threadA.start();
        threadB.start();
        System.out.println("wait all thread");

        // 主线程会在调用 join 方法后被阻塞，等待线程完成后继续执行
        threadA.join();
        threadB.join();

        System.out.println("all over");
    }
}
