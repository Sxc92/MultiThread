package com.christ.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * future 线程池使用demo
 *
 * @author 史偕成
 * @date 2023/09/04 11:01
 **/
public class FutureThreadPoolDemo {

//    static List<FutureTask<String>> futureTasks = new ArrayList<FutureTask<String>>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            addFutureTask("task" + i, 500, threadPool);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("-----costTIme: " + (endTime - startTime) + " 毫秒");
        threadPool.shutdown();
    }


    private static void addFutureTask(String name, long time, ExecutorService threadPool) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + " over";
        });
        threadPool.submit(futureTask);


//        System.out.println(futureTask.get());

        // 通过轮询的方式查询异步任务结果
        while (true) {
            // 重点
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("请等待执行完成。。。。。");
            }
        }
    }
}
