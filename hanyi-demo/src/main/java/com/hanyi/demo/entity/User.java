package com.hanyi.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 用户实体类
 *
 * @author wcwei@iflytek.com
 * @since 2020-12-08 11:00
 */
@Data
public class User implements Serializable {

    private Integer id;

    private Integer age;

    private String name;

    private String sex;

    private Date createDate;

    private Map<Integer, Integer> dataMap;

}
