package com.qin.chapter11;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TreeMap;

public class SDFTest2 {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(() -> {
				try {
					synchronized (sdf) {
						System.out.println(sdf.parse("2020-02-05 19:30:12"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			});
			thread.start();
		}
	}
}

class SDFTest3 {
	static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(
			() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	);

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(() -> {
				try {
					System.out.println(sdf.get().parse("2020-02-05 19:30:12"));
				} catch (ParseException e) {
					e.printStackTrace();
				} finally {
					sdf.remove();
				}
			});
			thread.start();
		}
	}
}