package com.christ.future;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author 史偕成
 * @date 2023/09/05 10:11
 **/
@Slf4j
public class CompletableFutureUseDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        completableFuture();
    }


    public static void completableFuture() throws ExecutionException, InterruptedException {
        // 定义一个自己的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> completableFutureResult = null;
        try {
            completableFutureResult = CompletableFuture.supplyAsync(() -> {
                log.info("----- come in ");
                // 10以内的随机数字
                int i = ThreadLocalRandom.current().nextInt(10);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------1秒钟获取结果");
                if (i > 5) {
                    i = 10 / 0;
                }
                return i;
            }, threadPool).whenComplete((v, e) -> {
                if (e == null) {
                    log.info("上一步任务执行成功！");
                }
            }).exceptionally(e -> {
                log.error("出现异常", e);
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
        log.info("可以先去忙别的事情");

        log.info("获取结果：{}", completableFutureResult.get());
    }

    /**
     * future 方式
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void future() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFutureResult = CompletableFuture.supplyAsync(() -> {
            log.info("----- come in ");
            // 10以内的随机数字
            int i = ThreadLocalRandom.current().nextInt(10);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("------1秒钟获取结果");
            return i;
        });

        log.info("可以先去忙别的事情");

        log.info("获取结果：{}", completableFutureResult.get());
    }
}
