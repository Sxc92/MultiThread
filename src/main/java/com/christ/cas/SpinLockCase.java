package com.christ.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现一个自旋锁
 *
 * @author 史偕成
 * @date 2023/09/07 14:56
 **/
@Slf4j
public class SpinLockCase {

    /**
     * 定义一个包装线程类
     */
    AtomicReference<Thread> treadAtomic = new AtomicReference<Thread>();

    public void lock() {
        Thread thread = Thread.currentThread();
        log.info("come in ");
        while (!treadAtomic.compareAndSet(null, thread)) {
//            log.info("等待其他线程释放");
        }
        log.info("成功获取锁");
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        treadAtomic.compareAndSet(thread, null);
        log.info("锁释放成功 ");
    }

    public static void main(String[] args) {
        SpinLockCase spinLockCase = new SpinLockCase();
        new Thread(() -> {
            spinLockCase.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinLockCase.unLock();
        }, "A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockCase.lock();
            spinLockCase.unLock();
        }, "B").start();
    }
}
