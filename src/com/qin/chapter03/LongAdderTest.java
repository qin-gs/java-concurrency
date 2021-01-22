package com.qin.chapter03;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

public class LongAdderTest {

	LongAdder adder = new LongAdder();
	// 设定累加规则
	LongAccumulator accumulator = new LongAccumulator(new LongBinaryOperator() {
		@Override
		public long applyAsLong(long left, long right) {
			return left + right;
		}
	}, 0);

	public static void main(String[] args) {

	}
}
