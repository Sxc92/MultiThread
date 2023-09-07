package com.christ.future;

import java.util.concurrent.*;

/**
 * @author 史偕成
 * @date 2023/09/05 09:51
 **/
public class CompletableFutureBuild {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, threadPool);
        System.out.println(voidCompletableFuture.get());


        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务执行完成";
        }, threadPool);
        System.out.println(stringCompletableFuture.get());


        threadPool.shutdown();
    }
}
