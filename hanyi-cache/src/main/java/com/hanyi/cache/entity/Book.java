package com.hanyi.cache.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @PackAge: middleground com.hanyi.cache.entity
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-12 20:33
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    private Integer id;

    private String name;

    private String author;

}
