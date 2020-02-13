package com.hanyi.daily.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author weiwenchang
 * @since 2020-02-11
 */
@ConfigurationProperties(prefix = "person")
@Component
@Data
public class PersonProperty {

    private String name;
    private Integer age;
    private List<String> stringList;
    private List<Book> bookList;

}
