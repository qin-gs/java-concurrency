package com.qin.chapter10;

import java.util.concurrent.CountDownLatch;

public class JoinCountDownLatch {

	private static volatile CountDownLatch latch = new CountDownLatch(2);

	public static void main(String[] args) throws InterruptedException {
		Thread threadOne = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				latch.countDown();
			}
			System.out.println("child threadOne over");
		});
		Thread threadTwo = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				latch.countDown();
			}
			System.out.println("child threadTwo over");
		});

		threadOne.start();
		threadTwo.start();

		System.out.println("wait all child thread over");

		latch.await();

		System.out.println("all child thread over");
	}
}
