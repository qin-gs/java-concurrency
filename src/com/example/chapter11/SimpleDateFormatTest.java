package com.example.chapter11;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SimpleDateFormatTest {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    sdf.parse("2021-01-31 17:04:32");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
