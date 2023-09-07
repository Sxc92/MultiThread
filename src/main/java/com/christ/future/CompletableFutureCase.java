package com.christ.future;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 现实案例
 *
 * @author 史偕成
 * @date 2023/09/05 11:11
 **/
@Slf4j
public class CompletableFutureCase {

    static List<Data> list = Arrays.asList(
            new Data("jd"),
            new Data("dangdang"),
            new Data("taobao")
    );

    public static List<String> getPrice(List<Data> list, String productName) {
        return list.stream()
                .map(data -> String.format(productName + "in %s price is %.2f", data.getSourceName(), data.calcPrice(productName)))
                .collect(Collectors.toList());
    }


    public static List<String> getPriceByCompletable(List<Data> list, String productName) {
        return list.stream().map(data -> CompletableFuture.supplyAsync(() ->
                        String.format(productName + "in %s price is %.2f", data.getSourceName(), data.calcPrice(productName))))
                .toList().stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> listData = getPrice(list, "mysql");
        listData.forEach(System.out::println);

        long endTime = System.currentTimeMillis();
        log.info("---costTime: {} 毫秒", (endTime - startTime));
    }




}

class Data {
    @Getter
    private String sourceName;

    public Data(String sourceName) {
        this.sourceName = sourceName;
    }


    public BigDecimal calcPrice(String sourceName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e) {
            e.printStackTrace();
        }
        double v = ThreadLocalRandom.current().nextDouble() * 2 + sourceName.charAt(0);
        return new BigDecimal(v);
    }
}
