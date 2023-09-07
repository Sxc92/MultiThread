package com.christ.lockSupport;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 史偕成
 * @date 2023/09/06 14:43
 **/
@Slf4j
public class LockSupportCase {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程：{}, ---- come in ", Thread.currentThread().getName());
            LockSupport.park();
            log.info("线程：{}, ---- 被唤醒 ", Thread.currentThread().getName());
        }, "t1");
        t1.start();

//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        new Thread(() -> {
            LockSupport.unpark(t1);
            log.info("线程：{}, 进行唤醒 ", Thread.currentThread().getName());
        }, "t2").start();
    }
}

/**
 * Object 案例  synchronized wait notify
 */
@Slf4j
class ObjectLockSupportCase {
    public static void main(String[] args) {
        Object obj = new Object();
        new Thread(() -> {
            synchronized (obj) {
                log.info("线程：{}, ---- come in ", Thread.currentThread().getName());
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("线程：{}, ---- 被唤醒 ", Thread.currentThread().getName());
            }
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            synchronized (obj) {
                obj.notify();
                log.info("线程：{}, 进行唤醒 ", Thread.currentThread().getName());
            }
        }, "t2").start();
    }
}

@Slf4j
class JucLockSupportCase {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                log.info("线程：{}, ---- come in ", Thread.currentThread().getName());
                condition.await();
                log.info("线程：{}, ---- 被唤醒 ", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                log.info("线程：{}, 进行唤醒 ", Thread.currentThread().getName());
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
