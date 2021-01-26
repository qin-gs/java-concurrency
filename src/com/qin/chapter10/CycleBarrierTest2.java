package com.qin.chapter10;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CycleBarrierTest2 {
	private static final CyclicBarrier barrier = new CyclicBarrier(2);

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(2);

		service.submit(() -> {
			try {
				System.out.println(Thread.currentThread() + " step1");
				barrier.await();
				System.out.println(Thread.currentThread() + " step2");
				barrier.await();
				System.out.println(Thread.currentThread() + " step3");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		service.submit(() -> {
			try {
				System.out.println(Thread.currentThread() + " step1");
				barrier.await();
				System.out.println(Thread.currentThread() + " step2");
				barrier.await();
				System.out.println(Thread.currentThread() + " step3");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		service.shutdown();
	}
}
