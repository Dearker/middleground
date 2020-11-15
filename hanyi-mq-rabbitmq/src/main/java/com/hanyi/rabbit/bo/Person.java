package com.hanyi.rabbit.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author wenchangwei
 * @since 5:14 下午 2020/11/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    private static final long serialVersionUID = -6000312760755529144L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

}
