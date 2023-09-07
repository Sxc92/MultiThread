package com.christ.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 史偕成
 * @date 2023/09/07 15:48
 **/
@Slf4j
public class ABAProblemCase {

    static AtomicInteger atomicInteger = new AtomicInteger(100);

    public static void main(String[] args) {
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
