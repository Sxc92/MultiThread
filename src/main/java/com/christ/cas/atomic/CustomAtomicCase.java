package com.christ.cas.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

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

public class CustomAtomicCase {

}
