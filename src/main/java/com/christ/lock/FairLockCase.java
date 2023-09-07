package com.christ.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 史偕成
 * @date 2023/09/05 16:40
 **/
@Slf4j
public class FairLockCase {

    private int number = 50;

    /**
     * 设置公平锁
     */
    ReentrantLock lock = new ReentrantLock(true);

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                log.info("{} 卖出第: \t {} \t 还剩下：{} ", Thread.currentThread().getName(), number--, number);
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        FairLockCase ticket = new FairLockCase();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "a").start();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "b").start();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "c").start();
    }
}

//class FairLockCaseDemo {
//
//}
