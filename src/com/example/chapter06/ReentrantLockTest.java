package com.example.chapter06;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现一个线程安全的List
 */
public class ReentrantLockTest<E> {

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
