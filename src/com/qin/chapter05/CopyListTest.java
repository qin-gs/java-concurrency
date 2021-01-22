package com.qin.chapter05;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyListTest {

	private static volatile CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

	public static void main(String[] args) throws InterruptedException {
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");

		Thread threadOne = new Thread(() -> {
			list.set(1, "bb");
			list.remove(2);
			list.remove(3);
		});

		Iterator<String> itr = list.iterator();
		threadOne.start();
		threadOne.join();

		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
}
