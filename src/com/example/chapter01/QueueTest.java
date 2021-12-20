package com.example.chapter01;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 通过 while 避免虚假唤醒
 * 需要先通过 synchronized 拿到了监视器锁，否则 wait 会抛出 IllegalMonitorException 异常
 */
public class QueueTest {

    private static final int MAX_SIZE = 10;
    private final Queue<String> queue = new LinkedList<>();

    public void producer(String element) {
        // 先获取监视器锁
        synchronized (queue) {
            // 如果队列已满，就挂起当前线程(前面通过 synchronized 拿到了监视器锁，否则 wait 会抛出 IllegalMonitorException 异常)
            // 同时使用 while 循环，避免虚假唤醒
            while (queue.size() == MAX_SIZE) {
                try {
                    // 挂起当前线程，同时会释放当前共享变量上已获取到的锁(否则会死锁)
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(element);
            queue.notifyAll();
        }
    }

    public void consumer() {
        synchronized (queue) {
            while (queue.size() == 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.poll();
            queue.notifyAll();
        }
    }
}
