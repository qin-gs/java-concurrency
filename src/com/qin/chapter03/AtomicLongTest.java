package com.qin.chapter03;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {

    private static AtomicLong atomicLong = new AtomicLong();
    private static Integer[] arrayOne = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0};
    private static Integer[] arrayTwo = new Integer[]{1, 3, 0, 0, 0, 1, 2, 6, 7, 8, 0, 0, 0};


    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            int size = arrayOne.length;
            for (Integer integer : arrayOne) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();
                }
            }
        });

        Thread threadTwo = new Thread(() -> {
            int size = arrayTwo.length;
            for (Integer integer : arrayTwo) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();
                }
            }
        });

        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();

        System.out.println("atomicLong.get() = " + atomicLong.get());
    }
}
