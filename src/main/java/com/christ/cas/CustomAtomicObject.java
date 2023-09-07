package com.christ.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 史偕成
 * @date 2023/09/07 14:49
 **/

public class CustomAtomicObject {

    public static void main(String[] args) {
        AtomicReference<User> userAtomic = new AtomicReference<User>();
        User user = new User("11", 11);
        User user2 = new User("22", 22);
        userAtomic.set(user);

        System.out.println(userAtomic.compareAndSet(user, user2));
        System.out.println(userAtomic.get());
    }
}


@ToString
@Data
@AllArgsConstructor
class User {
    private String name;
    private int age;
}
