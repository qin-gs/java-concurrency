package com.example.chapter06;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 工具类用来挂起和唤醒线程
 */
public class LockSupportTest {
    public static void main(String[] args) throws InterruptedException {
        test5();
    }

    public static void test1() {
        System.out.println("begin park");
        LockSupport.park(); // 默认情况下，调用线程是不持有许可证的
        System.out.println("end park");
    }

    public static void test2() {
        System.out.println("begin park");
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("end park");
    }

    public static void test3() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("child thread begin park");
            // 调用 park 方法，挂起自己
            LockSupport.park(); // 获取许可证之后就可以返回了
            System.out.println("child thread unpark");
        });
        thread.start();
        Thread.sleep(1000);

        System.out.println("main thread begin unpark");
        // 主线程调用 unpark 方法，子线程作为参数，让子线程获取许可证，然后方法才能返回
        LockSupport.unpark(thread);
    }

    public static void test4() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("child thread begin park");
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
            }
            System.out.println("child thread unpark");
        });
        thread.start();
        Thread.sleep(1000);

        System.out.println("main thread begin unpark");
        thread.interrupt(); // 只有中断子线程，子线程才会终止运行
    }

    public static void test5() {
        LockSupportTest test = new LockSupportTest();
        test.testPark();
    }

    private void testPark() {
        // 带有 blocker 参数的 park 方法会被记录到线程内部，编译诊断线程被阻塞的原因
        LockSupport.park(this);
    }
}
