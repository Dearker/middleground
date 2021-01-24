package com.hanyi.daily.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 15:24 2020/5/21
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private int id;

    private String name;

}
