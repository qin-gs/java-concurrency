package com.example.chapter11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShutDownTest {
    static void asyncExecuteOne() {
        // 线程池创建的都是用户线程，不是守护线程(只有不存在用户线程时JVM才会退出)
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> System.out.println("async execute one"));
        service.shutdown();
    }

    static void asyncExecuteTwo() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> System.out.println("async execute two"));
    }

    public static void main(String[] args) {
        System.out.println("async execute");
        asyncExecuteOne();
        asyncExecuteTwo();
        System.out.println("execute over");
    }
}
