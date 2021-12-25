package com.example.chapter04;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * LongAdder 是 LongAccumulator 的一个特例
 */
public class LongAdderTest {

    LongAdder adder = new LongAdder();

    /**
     * 设定累加规则,
     * 传入累加器 和 初始值0
     */
    LongAccumulator accumulator = new LongAccumulator(new LongBinaryOperator() {
        @Override
        public long applyAsLong(long left, long right) {
            return left + right;
        }
    }, 0);

    public static void main(String[] args) {
        // 每次都相乘
        LongAccumulator longAccumulator = new LongAccumulator((left, right) -> left * right, 1);
        longAccumulator.accumulate(3);
        longAccumulator.accumulate(4);
        System.out.println(longAccumulator.get());
    }
}
