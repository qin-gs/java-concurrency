package com.qin.chapter06;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 使用ReentrantReadWriteLock实现一个线程安全的List
 */
public class ReentrantReadWriteLockTest<E> {

    private ArrayList<E> list = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReadLock readLock = lock.readLock();
    private final WriteLock writeLock = lock.writeLock();

    public void add(E e) {
        writeLock.lock();
        try {
            list.add(e);
        } finally {
            writeLock.unlock();
        }
    }

    public void remove(E e) {
        writeLock.lock();
        try {
            list.remove(e);
        } finally {
            writeLock.unlock();
        }
    }

    public E get(int index) {
        readLock.lock();
        try {
            return list.get(index);
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {

    }
}
