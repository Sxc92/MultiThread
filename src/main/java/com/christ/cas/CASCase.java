package com.christ.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 史偕成
 * @date 2023/09/07 14:15
 **/
@Slf4j
public class CASCase {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        new Thread(() -> {
            atomicInteger.compareAndSet(5, 2000);
        }, "t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e) {
                e.printStackTrace();
            }
            atomicInteger.compareAndSet(2000, 2003);
            log.info("最终结果02:{}", atomicInteger.get());
        }, "t2").start();

        log.info("最终结果01:{}", atomicInteger.get());

    }
}
