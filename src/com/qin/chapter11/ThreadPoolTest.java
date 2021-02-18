package com.qin.chapter11;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	static class LocalVariable {
		private Long[] a = new Long[1024 * 1024];
	}

	final static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 1,
			TimeUnit.MINUTES, new LinkedBlockingQueue<>());
	final static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 50; i++) {
			executor.execute(() -> {
				localVariable.set(new LocalVariable());
				System.out.println("use local variable");
				localVariable.remove();
			});
			Thread.sleep(1000);
		}
		System.out.println("pool execute over");
	}

}
