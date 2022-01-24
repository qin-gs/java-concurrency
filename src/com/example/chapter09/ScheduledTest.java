package com.example.chapter09;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledTest {
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

		// 只执行异常
		executor.schedule(() -> {
			System.out.println("a");
		}, 1, TimeUnit.SECONDS);

		// 固定频率
		// 执行规则 1 + n * 3
		// 如果上次任务没有还没有执行完成，不会并发执行
		executor.scheduleAtFixedRate(() -> {
			System.out.println("a");
		}, 1, 3, TimeUnit.SECONDS);

		// 固定时间间隔 执行规则 1 + 执行时间 + n * 3
		executor.scheduleWithFixedDelay(() -> {
			System.out.println("b");
		}, 1, 3, TimeUnit.SECONDS);
	}
}
