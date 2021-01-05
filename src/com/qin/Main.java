package com.qin;

public class Main {

    public static void main(String[] args) {
        try {
            new Object().wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
