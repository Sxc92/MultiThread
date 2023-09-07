package com.christ.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 史偕成
 * @date 2023/09/03 18:00
 **/
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

        // 打印获取异步任务执行返回结果
        System.out.println(futureTask.get());
//        System.out.println(futureTask.get());
    }

}

//class MyThread implements Runnable {
//
//    @Override
//    public void run() {
//
//    }
//}

class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("----- come in call()");
        return "hello Callable";
    }
}
