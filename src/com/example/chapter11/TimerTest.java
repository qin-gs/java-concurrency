package com.example.chapter11;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerTest {
    static final Timer timer = new Timer();

    public static void main(String[] args) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(" one task ");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("an error");
            }
        }, 500);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (; ; ) {
                    System.out.println(" two task ");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);

    }
}
