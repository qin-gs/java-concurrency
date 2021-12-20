package com.example.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * wait 方法只会释放当前共享对象的锁，不会释放已获取的其他变量的锁
 */
public class DeadLockTest {
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            try {
                synchronized (resourceA) {
                    System.out.println("threadA get resourceA");
                    synchronized (resourceB) {
                        System.out.println("threadA get resourceB");
                        // threadA 执行到这里会获取到两个锁
                        // wait 方法只会释放 resourceA 上的锁，resourceB 上的锁不会释放
                        resourceA.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                synchronized (resourceA) {
                    System.out.println("threadB get resourceA");
                    // threadA 只会释放 resourceA 上的锁，所以 threadB 会在这里被阻塞无法运行
                    synchronized (resourceB) {
                        System.out.println("threadB get resourceB");
                        resourceA.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("main over");
    }
}
