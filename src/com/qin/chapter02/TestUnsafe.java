package com.qin.chapter02;

import sun.misc.Unsafe;

public class TestUnsafe {
    static final Unsafe unsafe = Unsafe.getUnsafe();
    static final long stateOffset;
    private volatile long state = 0;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static void main(String[] args) {
        TestUnsafe test = new TestUnsafe();
        boolean success = unsafe.compareAndSwapLong(test, stateOffset, 0, 1);
        System.out.println("success = " + success);
        // 由于使用了不同的类加载器，抛异常
    }
}
