package com.example.chapter02;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestUnsafeReflect {

    static final Unsafe unsafe;
    static final long stateOffset;
    private volatile long state = 0;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset(TestUnsafeReflect.class.getDeclaredField("state"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static void main(String[] args) {
        TestUnsafeReflect test = new TestUnsafeReflect();
        boolean success = unsafe.compareAndSwapLong(test, stateOffset, 0, 1);
        System.out.println("success = " + success);
    }
}
