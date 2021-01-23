package com.qin.chapter06;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

public class ConsumerTest {

	final static NonReentrantLock lock = new NonReentrantLock();
	final static Condition notFull = lock.newCondition();
	final static Condition notEmpty = lock.newCondition();

	final static Queue<String> queue = new LinkedBlockingQueue<>();
	final static int queueSize = 10;

	public static void main(String[] args) {
		Thread producer = new Thread(() -> {
			lock.lock();
			try {
				while (queue.size() == queueSize) {
					notEmpty.await();
				}
				queue.add("element");
				notFull.signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		});

		Thread consumer = new Thread(() -> {
			lock.lock();
			try {
				while (queue.size() == 0) {
					notFull.await();
				}
				String element = queue.poll();
				notEmpty.signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		});

		producer.start();
		consumer.start();
	}

}
