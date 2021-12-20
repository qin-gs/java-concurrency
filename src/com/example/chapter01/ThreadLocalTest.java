package com.example.chapter01;

public class ThreadLocalTest {

    static ThreadLocal<String> localVariable = new ThreadLocal<>();

    static void print(String str) {
        System.out.println(str + ": " + localVariable.get());
        // localVariable.remove();
    }

    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            localVariable.set("thread one local variable");
            print("thread one");
            System.out.println("thread one after" + ": " + localVariable.get());
        });

        Thread two = new Thread(() -> {
            localVariable.set("thread two local variable");
            print("thread two");
            System.out.println("thread two after" + ": " + localVariable.get());
        });

        one.start();
        two.start();
    }
}
