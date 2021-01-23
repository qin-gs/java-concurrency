package com.qin.chapter06;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

	private static final ReentrantLock lock = new ReentrantLock();
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
		System.out.println("begin signal");
		condition.signal();
		System.out.println("end signal");
		lock.unlock();
	}
}
