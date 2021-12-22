package com.example.chapter01;

/**
 * InheritableThreadLocal 可以从子线程中获取到父线程变量的值
 */
public class InheritableThreadLocalTest {
    public static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello world");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(threadLocal.get());
            }
        }).start();

        System.out.println(threadLocal.get());
    }
}
