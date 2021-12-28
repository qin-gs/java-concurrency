package com.example.chapter06;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用锁创建条件变量
 */
public class ConditionTest {

    private static final ReentrantLock lock = new ReentrantLock();
    // 使用锁对象创建一个条件变量
    private static final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        lock.lock();
        try {
            System.out.println("begin wait");
            condition.await();
            System.out.println("end wait");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        lock.lock();
        try {
            System.out.println("begin signal");
            condition.signal();
            System.out.println("end signal");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
