package com.qin.chapter06;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 实现一个独占不可重入锁，支持条件变量
 */
public class NonReentrantLock implements Lock, Serializable {

	/**
	 * 内部帮助类
	 */
	private static class Sync extends AbstractQueuedSynchronizer {

		/**
		 * 锁是否已经被持有
		 */
		@Override
		protected  boolean isHeldExclusively() {
			return getState() == 1;
		}

		/**
		 * 如果state为0，尝试获取锁
		 */
		@Override
		protected boolean tryAcquire(int acquires) {
			assert acquires == 1;
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		/**
		 * 尝试释放锁，并将state设为0
		 */
		@Override
		protected boolean tryRelease(int acquires) {
			assert acquires == 1;
			if (getState() == 0) {
				throw new IllegalMonitorStateException();
			}
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}

		Condition newCondition() {
			return new ConditionObject();
		}
	}

	private final Sync sync = new Sync();

	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return null;
	}
}
