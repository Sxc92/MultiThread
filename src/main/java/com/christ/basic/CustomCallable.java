package com.christ.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 史偕成
 * @date 2023/08/21 16:51
 **/
public class CustomCallable<T> implements Callable {
    public String call() throws Exception {
        return "Hello,i am running!";
    }

    public static void main(String[] args) {
        //创建异步任务
        FutureTask<String> task = new FutureTask<>(new CustomCallable());
        //启动线程
        new Thread(task).start();
        try {
            //等待执行完成，并获取返回结果
            String result = task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
