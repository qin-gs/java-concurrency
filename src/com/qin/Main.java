package com.qin;

import java.util.Arrays;

public class Main {

    static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() -> local.set("son thread"));

        local.set("main thread");
    }
}
