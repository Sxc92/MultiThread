package com.christ.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 史偕成
 * @date 2023/09/05 17:13
 **/
@Slf4j
public class ReEntryLock {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                log.info("{}, \t -----外层调用", Thread.currentThread().getName());
                lock.lock();
                try {
                    log.info("{}, \t -----中层调用", Thread.currentThread().getName());
                    lock.lock();
                    try {
                        log.info("{}, \t -----内层调用", Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                    }
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "t1").start();
    }

    /**
     *
     */
    public static void reEntryMethod() {

    }

    /**
     * 重入代码块
     */
    public static void reEntryBlock() {
        final Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                log.info("{}, \t -----外层调用", Thread.currentThread().getName());
                synchronized (object) {
                    log.info("{}, \t -----中层调用", Thread.currentThread().getName());
                    synchronized (object) {
                        log.info("{}, \t -----内层调用", Thread.currentThread().getName());
                    }
                }
            }
        }, "t1").start();
    }
}
