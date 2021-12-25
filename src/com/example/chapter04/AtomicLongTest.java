package com.example.chapter04;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 原子性的递增或递减类，使用 Unsafe 实现
 */
public class AtomicLongTest {

    private static final AtomicLong atomicLong = new AtomicLong();
    private static final Integer[] arrayOne = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0};
    private static final Integer[] arrayTwo = new Integer[]{1, 3, 0, 0, 0, 1, 2, 6, 7, 8, 0, 0, 0};


    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            for (Integer integer : arrayOne) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();
                }
            }
        });

        Thread threadTwo = new Thread(() -> {
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
