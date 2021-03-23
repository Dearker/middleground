package com.hanyi.daily.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2:26 下午 2020/7/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String id;

    private String name;

    private int age;

    private double totalPrice;

    private LocalDateTime createTime;

    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
