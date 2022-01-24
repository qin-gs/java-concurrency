package com.example.chapter07;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {

    static class DelayEle implements Delayed {
        private final long delayTime;
        private final long expire;
        private final String taskName;

        public DelayEle(long delayTime, String taskName) {
            this.delayTime = delayTime;
            this.taskName = taskName;
            this.expire = System.currentTimeMillis() + delayTime;
        }

        /**
         * 获取剩余多长时间过期
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 获取优先级规则
         */
        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "DelayEle{" +
                    "delayTime=" + delayTime +
                    ", expire=" + expire +
                    ", taskName='" + taskName + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        DelayQueue<DelayEle> queue = new DelayQueue<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            DelayEle ele = new DelayEle(random.nextInt(500), "task: " + i);
            queue.offer(ele);
        }

        // 等待任务过期，取出
        DelayEle ele = null;
        try {
            for (; ; ) {
                while ((ele = queue.take()) != null) {
                    System.out.println(ele);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
