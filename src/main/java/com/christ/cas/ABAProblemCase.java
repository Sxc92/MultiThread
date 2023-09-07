package com.christ.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 史偕成
 * @date 2023/09/07 15:48
 **/
@Slf4j
public class ABAProblemCase {

    public static void main(String[] args) {
        finish();
    }


    private static void finish() {
        AtomicStampedReference<Integer> data =
                new AtomicStampedReference<Integer>(100, 1);

        new Thread(() -> {
            log.info("获取的首次版本号: {}", data.getStamp());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            data.compareAndSet(100, 101, data.getStamp(), data.getStamp() + 1);
            log.info("获取的二次版本号: {}", data.getStamp());

            data.compareAndSet(101, 100, data.getStamp(), data.getStamp() + 1);
            log.info("获取的三次版本号: {}", data.getStamp());
        }, "A").start();


        new Thread(() -> {
            int stamp = data.getStamp();
            log.info("获取的首次版本号: {}", stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean b = data.compareAndSet(100, 2000, stamp, stamp + 1);
            log.info("操作结果：{} 数据版本号: {}，数据结果: {}", b, data.getStamp(), data.getReference());
        }, "B").start();
    }

    /**
     * 问题演示
     */
    private static void problem() {
        AtomicInteger atomicInteger = new AtomicInteger(100);
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("A线程获取数据：{}", atomicInteger.get());
            atomicInteger.compareAndSet(101, 100);
            log.info("A线程获取数据：{}", atomicInteger.get());
        }, "A").start();


        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("B线程获取数据：{}", atomicInteger.get());
            atomicInteger.compareAndSet(100, 2000);
            log.info("执行了B线程");
            log.info("最终数据：{}", atomicInteger.get());
        }, "B").start();
    }
}
