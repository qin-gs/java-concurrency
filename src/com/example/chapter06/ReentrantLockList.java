package com.example.chapter06;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 ReentrantLock 实现一个线程安全的List
 * 每次增删元素之前都需要获取到锁
 */
public class ReentrantLockList<E> {

    // 线程不安全的 list
    private ArrayList<E> list = new ArrayList<>();
    private volatile ReentrantLock lock = new ReentrantLock();

    public void add(E e) {
        lock.lock();
        try {
            list.add(e);
        } finally {
            lock.unlock();
        }
    }

    public void remove(E e) {
        lock.lock();
        try {
            list.remove(e);
        } finally {
            lock.unlock();
        }
    }

    public E get(int index) {
        lock.lock();
        try {
            return list.get(index);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

    }
}
