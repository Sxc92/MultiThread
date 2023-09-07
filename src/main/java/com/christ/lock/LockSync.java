package com.christ.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 史偕成
 * @date 2023/09/05 15:38
 **/
@Slf4j
public class LockSync {

    Object object = new Object();

    public void m1() {
        synchronized (object) {
            log.info("synchronized code block");
        }
    }

    public synchronized void m2() {
        log.info("synchronized method");
    }


    public static synchronized void m3() {
        log.info("synchronized method");
    }

    public static void main(String[] args) {

    }
}
