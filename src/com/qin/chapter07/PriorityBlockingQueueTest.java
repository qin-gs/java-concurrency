package com.qin.chapter07;

import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import sun.font.FontRunIterator;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueTest {

	static class Task implements Comparable<Task> {

		private int priority;
		private String taskName;

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}

		public String getTaskName() {
			return taskName;
		}

		public void setTaskName(String taskName) {
			this.taskName = taskName;
		}

		@Override
		public int compareTo(Task o) {
			if (this.priority >= o.getPriority()) {
				return 1;
			} else {
				return -1;
			}
		}

		public void doSomething() {
			System.out.println(taskName + ": " + priority);
		}
	}

	public static void main(String[] args) {
		PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			Task task = new Task();
			task.setPriority(random.nextInt(10));
			task.setTaskName("taskName " + i);
			queue.offer(task);
		}
		while (!queue.isEmpty()) {
			Task task = queue.poll();
			if (task != null) {
				task.doSomething();
			}
		}
	}
}
