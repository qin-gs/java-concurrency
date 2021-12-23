package com.example.chapter02;

import java.time.Instant;

/**
 * 程序的局部性原理
 */
public class ContentTest {

    public static void main(String[] args) {
        first();
        second();
    }

    static final int LINE_NUM = 1024;
    static final int COLUMN_NUM = 1024;

    public static void first() {
        long[][] arr = new long[LINE_NUM][COLUMN_NUM];
        long start = Instant.now().toEpochMilli();
        // 数组按行访问要更快
        for (int i = 0; i < LINE_NUM; i++) {
            for (int j = 0; j < COLUMN_NUM; j++) {
                arr[i][j] = i * 2 + j;
            }
        }
        long end = Instant.now().toEpochMilli();
        System.out.println(end - start);
    }

    public static void second() {
        long[][] arr = new long[LINE_NUM][COLUMN_NUM];
        long start = Instant.now().toEpochMilli();
        for (int i = 0; i < COLUMN_NUM; i++) {
            for (int j = 0; j < LINE_NUM; j++) {
                arr[j][i] = i * 2 + j;
            }
        }
        long end = Instant.now().toEpochMilli();
        System.out.println(end - start);
    }

}
