package com.example.chapter03;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            System.out.println(random.nextInt(5));
        }

        ThreadLocalRandom tlRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 5; i++) {
            System.out.println("tlRandom.nextInt(5) = " + tlRandom.nextInt(5));
        }
    }
}
