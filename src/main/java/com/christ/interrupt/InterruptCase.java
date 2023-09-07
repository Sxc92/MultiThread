package com.christ.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 史偕成
 * @date 2023/09/05 18:25
 **/
@Slf4j
public class InterruptCase {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("{} \t is stop true 程序停止, 时间：{}", Thread.currentThread().getName(), System.currentTimeMillis());
                    break;
                }
                log.info("hello volatile");
            }
        }, "t1");
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            thread.interrupt();
            log.info("t2 线程发起了中断协商：{}", System.currentTimeMillis());
        }, "t2").start();
    }
}

/**
 * 通过AtomicBoolean 控制线程中断
 */
@Slf4j
class AtomicBooleanCase {
    static AtomicBoolean isStop = new AtomicBoolean(false);

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                if (isStop.get()) {
                    log.info("{} \t is stop true 程序停止", Thread.currentThread().getName());
                    break;
                }
                log.info("hello volatile");
            }
        }, "t1").start();


        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            isStop.set(true);
        }, "t2").start();
    }
}

/**
 * 变量控制案例
 */
@Slf4j
class VolatileVariableCase {
    static volatile boolean isStop = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    log.info("{} \t is stop true 程序停止", Thread.currentThread().getName());
                    break;
                }
                log.info("hello volatile");
            }
        }, "t1").start();


        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
