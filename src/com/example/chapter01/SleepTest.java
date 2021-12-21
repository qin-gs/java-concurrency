package com.example.chapter01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 调用 sleep 方法后，不会是否已拥有的监视器锁，
 * 当一个线程处于睡眠状态时，如果另外的线程中断了它，会在调用 sleep 方法处抛出异常
 */
public class SleepTest {
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取独占锁
                lock.lock();
                try {
                    System.out.println("threadA sleep");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("threadA awake");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取独占锁
                lock.lock();
                try {
                    System.out.println("threadB sleep");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("threadB awake");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
