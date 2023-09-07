package com.christ.basic;

/**
 * @author 史偕成
 * @date 2023/08/21 16:47
 **/
public class CustomRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {//sleep会发生异常要显示处理
                Thread.sleep(20);//暂停20毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "打了:" + i + "个小兵");
        }
    }


    public static void main(String[] args) {
        CustomRunnable customRunnable = new CustomRunnable();
        Thread t1 = new Thread(customRunnable, "张飞");
        Thread t2 = new Thread(customRunnable, "貂蝉");
        Thread t3 = new Thread(customRunnable, "吕布");
        t1.start();
        t2.start();
        t3.start();
    }
}
