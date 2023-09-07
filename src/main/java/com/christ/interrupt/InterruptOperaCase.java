package com.christ.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 操作Interrupt方法 会不会让线程立即结束  验证case
 *
 * @author 史偕成
 * @date 2023/09/06 14:02
 **/
@Slf4j
public class InterruptOperaCase {

    public static void main(String[] args) {
        operaBlockedCase();
    }


    public static void operaCase() {
        Thread thread = new Thread(() -> {
            for (int i = 1; i < 500; i++) {
                System.out.println("-----" + i);
            }
            System.out.println("t1调用interrupt() 中断标识02：" + Thread.currentThread().isInterrupted());
        }, "t1");
        thread.start();

        System.out.println("t1线程默认的中断标识：" + thread.isInterrupted());


        new Thread(() -> {
            thread.interrupt();
            System.out.println("t1调用interrupt() 中断标识01：" + thread.isInterrupted());
        }, "t2").start();

        // 休眠2秒之后  t1线程已经完成
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1调用interrupt() 中断标识02：" + Thread.currentThread().isInterrupted());
    }

    /**
     * 抛出java.lang.InterruptedException
     */
    public static void operaBlockedCase() {
        Thread thread = new Thread(() -> {
            for (int i = 1; i < 500; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println("-----" + i);
            }
            System.out.println("t1调用interrupt() 中断标识02：" + Thread.currentThread().isInterrupted());
        }, "t1");
        thread.start();

        System.out.println("t1线程默认的中断标识：" + thread.isInterrupted());


        new Thread(() -> {
            thread.interrupt();
            System.out.println("t1调用interrupt() 中断标识01：" + thread.isInterrupted());
        }, "t2").start();

        // 休眠2秒之后  t1线程已经完成
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1调用interrupt() 中断标识02：" + Thread.currentThread().isInterrupted());
    }
}
