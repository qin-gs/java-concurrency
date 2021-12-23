package com.example.chapter02;

import sun.misc.Unsafe;

/**
 * 由于类加载器限制，无法直接使用 Unsafe.getUnsafe() 获取 Unsafe 类
 */
public class TestUnsafe {
    /**
     * 获取 Unsafe 实例
     */
    static final Unsafe unsafe = Unsafe.getUnsafe();
    /**
     * 记录变量在类中的偏移量
     */
    static final long stateOffset;
    private volatile long state = 0;

    static {
        try {
            // 获取变量在类中的偏移量
            stateOffset = unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        TestUnsafe test = new TestUnsafe();
        boolean success = unsafe.compareAndSwapLong(test, stateOffset, 0, 1);
        System.out.println("success = " + success);
        // 由于使用了不同的类加载器，抛异常
        // getUnsafe 方法里面判断是不是 Bootstrap 加载器加载 TestUnsafe 类
        // TestUnsafe 是 AppClassLoader 加载的
    }
}
