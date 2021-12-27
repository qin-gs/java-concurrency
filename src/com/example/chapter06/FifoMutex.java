package com.example.chapter06;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 一个先进先出的锁，只有队首元素可以获取锁
 */
public class FifoMutex {

    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // 如果当前线程不是队首元素 或 锁已被其他线程获取，调用 park 挂起自己
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            LockSupport.park();
            // 如果 park 方法是因为中断返回的，忽略中断，重置中断标志
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }

        waiters.remove();
        // 如果其他线程中断了改线程，恢复中断标志
        if (wasInterrupted) {
            current.interrupt();
        }
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}
