package com.christ.cas.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author 史偕成
 * @date 2023/09/07 16:15
 **/
class CustomInteger {
    AtomicInteger number = new AtomicInteger();

    /**
     * 自增
     */
    public void increment() {
        number.getAndIncrement();
    }
}

@Slf4j
class CustomIntegerCase {

    public static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {
        CustomInteger customInteger = new CustomInteger();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 1; i <= SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 1000; j++) {
                        customInteger.increment();
                    }
                } finally {
                    countDownLatch.countDown();
                }

            }, "thread" + i).start();
        }
        countDownLatch.await();
        log.info("最终计算结果：{}", customInteger.number);
    }
}

@Slf4j
class CustomAtomicMarkableReferenceCase {

    static AtomicMarkableReference<Integer> data = new AtomicMarkableReference<Integer>(100, false);

    public static void main(String[] args) {
        new Thread(() -> {
            boolean marked = data.isMarked();
            log.info("获取标识位：{}", marked);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            data.compareAndSet(100, 101, marked, !marked);
        }, "t1").start();

        new Thread(() -> {
            boolean marked = data.isMarked();
            log.info("获取标识位：{}", marked);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean b = data.compareAndSet(101, 2000, marked, !marked);

            log.info("CAS result: {}, marked: {}, data:{}", b, data.isMarked(), data.getReference());

        }, "t2").start();
    }
}

public class CustomAtomicCase {

}
