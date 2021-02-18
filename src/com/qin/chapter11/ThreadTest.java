package com.qin.chapter11;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest {

	static final String THREAD_SAVE_ORDER = "THREAD_SAVE_ORDER";
	static final String THREAD_SAVE_ADDR = "THREAD_SAVE_ADDR";

	static ThreadPoolExecutor executorOne = new ThreadPoolExecutor(5, 5, 1,
			TimeUnit.MINUTES, new LinkedBlockingQueue<>());
	static ThreadPoolExecutor executorTwo = new ThreadPoolExecutor(5, 5, 1,
			TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new NamedThreadFactory("thread pool "));

	public static void main(String[] args) {
		test2();
	}


	public static void test1() {
		Thread threadOne = new Thread(() -> {
			System.out.println("保存订单线程");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			throw new RuntimeException();
		}, THREAD_SAVE_ORDER);

		Thread threadTwo = new Thread(() -> {
			System.out.println("保存地址");
		}, THREAD_SAVE_ADDR);

		threadOne.start();
		threadTwo.start();
	}

	public static void test2() {
		executorOne.execute(() -> {
			System.out.println("接受用户线程");
			throw new NullPointerException();
		});
		executorTwo.execute(() -> System.out.println("具体业务请求线程"));

		executorOne.shutdown();
		executorTwo.shutdown();
	}

	private static class NamedThreadFactory implements ThreadFactory {
		private static final AtomicInteger pollNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		public NamedThreadFactory(String name) {
			SecurityManager manager = System.getSecurityManager();
			group = (manager != null) ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
			if (name == null || name.isEmpty()) {
				name = "pool";
			}
			namePrefix = name + "-" + pollNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (thread.isDaemon()) {
				thread.setDaemon(false);
			}
			if (thread.getPriority() != Thread.MAX_PRIORITY) {
				thread.setPriority(Thread.NORM_PRIORITY);
			}
			return thread;
		}
	}
}
