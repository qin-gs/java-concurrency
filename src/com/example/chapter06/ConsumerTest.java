package com.example.chapter06;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * 使用 NonReentrantLock 实现生产者消费者模型
 */
public class ConsumerTest {

    final static com.example.chapter06.NonReentrantLock lock = new com.example.chapter06.NonReentrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static int queueSize = 10;

    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
            // 首先获取独占锁
            lock.lock();
            try {
                // 如果队列满了，等待消费者消费 (使用 while 避免虚假唤醒)
                while (queue.size() == queueSize) {
                    // 阻塞当前线程
                    notEmpty.await();
                }
                // 队列中放入元素
                queue.add("element");
                // 唤醒因为队列为空而被阻塞的消费线程
                notFull.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                lock.unlock();
            }
        });

        Thread consumer = new Thread(() -> {
            // 首先获取独占锁
            lock.lock();
            try {
                // 如果队列为空，等待
                while (queue.size() == 0) {
                    notFull.await();
                }
                // 从队列中取出元素
                String element = queue.poll();
                // 唤醒因为队列已满而被阻塞的生产线程
                notEmpty.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                lock.unlock();
            }
        });

        producer.start();
        consumer.start();
    }

}
