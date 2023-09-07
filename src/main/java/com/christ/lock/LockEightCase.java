package com.christ.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 锁的8个案例实现
 *
 * @author 史偕成
 * @date 2023/09/05 14:52
 **/
@Slf4j
public class LockEightCase {

    public static void main(String[] args) {
        case2();
    }

    /**
     * 两个线程访问 先打印啥？
     */
    public static void case1() {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.sendSms();
        }).start();
    }

    public static void case2() {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.sendSms();
        }).start();
    }
}

@Slf4j
class Phone {
    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("线程：{}, 发送电子邮件", Thread.currentThread().getName());
    }

    public synchronized void sendSms() {
        log.info("线程：{}, 发送短消息", Thread.currentThread().getName());
    }

    public void hello() {
        log.info("线程：{}, say hello", Thread.currentThread().getName());
    }
}