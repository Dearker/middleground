package com.hanyi.daily.property;

import lombok.Data;

import java.util.List;

/**
 * @author weiwenchang
 * @since 2020-02-11
 */
@Data
public class PersonDesc {

    private String name;
    private Integer age;
    private List<String> stringList;
    private List<Book> bookList;

}
