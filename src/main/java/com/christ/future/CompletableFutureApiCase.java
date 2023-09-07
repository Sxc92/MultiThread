package com.christ.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author 史偕成
 * @date 2023/09/05 13:35
 **/
@Slf4j
public class CompletableFutureApiCase {

    public static void main(String[] args) {
        thenApplyTest();
    }

    public static void thenApplyTest() {
        // 引入自定义线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);


        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("当前线程：{}", Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }, executor).thenApplyAsync(result -> {
            log.info("当前线程：{}", Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result + 1;
        }).thenApplyAsync(result -> {
            log.info("当前线程：{}", Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                log.info("result: {}", v);
            }
        }).exceptionally(e -> {
            log.info("出现异常了", e);
            return null;
        });

//        completableFuture.join();

//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Supplier executed on thread: " + Thread.currentThread().getName());
//            return 42;
//        }).thenApplyAsync(result -> {
//            System.out.println("thenApplyAsync 1 executed on thread: " + Thread.currentThread().getName());
//            return result * 2;
//        }, executor).thenApplyAsync(result -> {
//            System.out.println("thenApplyAsync 2 executed on thread: " + Thread.currentThread().getName());
//            return result * 3;
//        }, executor);

        executor.shutdown();
    }

    /**
     * 计算对比测试
     */
    public static void completeTest() {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "abc";
        });

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(stringCompletableFuture.complete("xxxx") + "\t" + stringCompletableFuture.join());
    }
}
