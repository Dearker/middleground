package com.hanyi.web.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author wcwei@iflytek.com
 * @since 2020-12-07 16:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -2379171125562759373L;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户年龄
     */
    private String age;

    /**
     * 生日
     */
    private Date birthDay;

}
