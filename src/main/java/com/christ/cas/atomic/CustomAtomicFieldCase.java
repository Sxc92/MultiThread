package com.christ.cas.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author 史偕成
 * @date 2023/09/07 16:54
 **/
@Slf4j
public class CustomAtomicFieldCase {


}
@Slf4j
class CustomAtomicReferenceFieldCase {

    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    bankAccount.init(bankAccount);
                }finally {
                    countDownLatch.countDown();
                }
            }, "t" + i).start();
        }
        countDownLatch.await();
        log.info("最终结果：{}", bankAccount.isInit);
    }
}

/**
 * 对Integer的局部数据变更
 */
@Slf4j
class CustomAtomicIntegerFieldCase {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 1000; j++) {
                        bankAccount.trans(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, "t" + i).start();
        }
        countDownLatch.await();
        log.info("最终结果：{}", bankAccount.money);
    }
}

@Slf4j
class BankAccount {
    String bankName = "CCB";

    // 添加public volatile
    public volatile int money = 0;

    public volatile Boolean isInit = Boolean.FALSE;

    //使用静态方法 针对局部需要修改的属性
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    public void trans(BankAccount account) {
        fieldUpdater.getAndIncrement(account);
    }


    AtomicReferenceFieldUpdater<BankAccount, Boolean> initFieldUpdater
            =AtomicReferenceFieldUpdater.newUpdater(BankAccount.class,Boolean.class,"isInit");
    public void init(BankAccount account) {
        if (initFieldUpdater.compareAndSet(account, Boolean.FALSE, Boolean.TRUE)) {
            log.info("come in wait tow seconds ");
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (Exception e) {
                e.printStackTrace();
            }
            log.info("init finish");
        } else {
            log.info("other thread get 。。。。 ");
        }
    }
}
