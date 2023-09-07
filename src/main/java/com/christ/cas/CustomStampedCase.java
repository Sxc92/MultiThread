package com.christ.cas;

import cn.hutool.log.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 史偕成
 * @date 2023/09/07 15:26
 **/
@Slf4j
public class CustomStampedCase {

//    AtomicStampedReference<Book> bookAtomicStampedReference = new AtomicStampedReference<Book>();

    public static void main(String[] args) {
        Book javaBook = new Book("java", 1);
        Book mysqlBook = new Book("mysql", 2);

        int initStamp = 1;
        AtomicStampedReference<Book> bookAtomicStampedReference = new AtomicStampedReference<Book>(javaBook, initStamp);
        log.info("初始化： {}, {}", bookAtomicStampedReference.getReference(), bookAtomicStampedReference.getStamp());

        new Thread(() -> {
            boolean b = bookAtomicStampedReference.compareAndSet(javaBook, mysqlBook, bookAtomicStampedReference.getStamp(), bookAtomicStampedReference.getStamp() + 1);
            log.info("第一次： {}, {}, 是否修改成功： {}", bookAtomicStampedReference.getReference(), bookAtomicStampedReference.getStamp(), b);
            boolean c = bookAtomicStampedReference.compareAndSet(mysqlBook, javaBook, bookAtomicStampedReference.getStamp(), bookAtomicStampedReference.getStamp() + 1);
            log.info("第二次： {}, {},  是否修改成功： {}", bookAtomicStampedReference.getReference(), bookAtomicStampedReference.getStamp(), c);
        }, "A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);

        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            boolean b = bookAtomicStampedReference.compareAndSet(javaBook, mysqlBook, initStamp, initStamp + 1);
            log.info("最终： {}, {}, 是否修改成功： {}", bookAtomicStampedReference.getReference(), bookAtomicStampedReference.getStamp(), b);
        }, "B").start();
    }
}

@ToString
@Data
@AllArgsConstructor
class Book {
    private String name;
    private int id;
}