package com.christ.basic;

/**
 * @author 史偕成
 * @date 2023/08/21 15:09
 **/

public class CustomThread extends Thread {

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + ":打了" + i + "个小兵");
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    public static void main(String[] args) {
        CustomThread customThread1 = new CustomThread();
        CustomThread customThread2 = new CustomThread();
        CustomThread customThread3 = new CustomThread();
        customThread1.setName("customThread1");
        customThread2.setName("customThread2");
        customThread3.setName("customThread3");
        try {
            customThread1.start();
            customThread1.join();
            customThread3.start();
            customThread3.join();
            customThread2.start();
            customThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("main线程执行完毕");
    }
}
