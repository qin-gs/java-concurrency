package com.example.chapter05;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 写时复制
 * <p>
 * 使用 synchronized 进行加锁控制(没有使用 ReentrantLock)
 */
public class CopyListTest {

    private static volatile CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        Thread threadOne = new Thread(() -> {
            list.set(1, "bb");
            list.remove(2);
            list.remove(3);
        });

        // 拿到迭代器之后，其他线程之后进行的修改不会显示出来(弱一致性)
        Iterator<String> itr = list.iterator();
        threadOne.start();
        threadOne.join();

        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}
