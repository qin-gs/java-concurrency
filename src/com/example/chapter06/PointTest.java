package com.example.chapter06;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock 操作
 */
public class PointTest {
    private double x, y;

    private final StampedLock lock = new StampedLock();

    /**
     * 排他锁
     */
    void move(double x, double y) {
        // 获取排他锁，其他线程到这里会被阻塞
        long stamp = lock.writeLock();
        try {
            this.x += x;
            this.y += y;
        } finally {
            lock.unlock(stamp);
        }
    }

    /**
     * 乐观读锁
     */
    double distance() {
        // 获取乐观锁，没有通过 cas 修改锁状态，只是简单通过与操作返回了个版本信息
        long stamp = lock.tryOptimisticRead();
        // 将变量复制到 栈帧 中
        double currentX = x;
        double currentY = y;
        // 如果之前获取的 stamp 之后，没有其他线程抢占，直接计算
        // 如果被其他线程抢占了，之前的 stamp 就无效了，需要重新获取锁
        if (!lock.validate(stamp)) {
            // 被抢占了，获取一个共享读锁(悲观获取)
            // 如果被其他线程获取了，这里会被阻塞直到其他线程释放锁
            long s = lock.readLock();
            try {
                // 因为其他线程获取了锁，值可能被修改了，需要重写复制变量
                currentX = x;
                currentY = y;
            } finally {
                // 是否共享读锁
                lock.unlockRead(s);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    /**
     * 使用悲观锁获取读锁，尝试转换为写锁
     */
    void moveIfOrigin(double newX, double newY) {
        // 获取一个悲观读锁，保证其他线程不能修改值
        long stamp = lock.readLock();
        try {
            while (x == 0 && y == 0) {
                // 尝试将获取的读锁更新为写锁(不一定成功)
                long wl = lock.tryConvertToWriteLock(stamp);
                // 如果更新成功，更新 stamp
                if (wl != 0L) {
                    stamp = wl;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 如果更新失败，释放读锁
                    lock.unlockRead(stamp);
                    // 显式获取独占锁写锁，循环重试
                    stamp = lock.writeLock();
                }
            }
        } finally {
            lock.unlock(stamp);
        }
    }
}
