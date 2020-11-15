package com.hanyi.rabbit.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户详情实体类
 * </p>
 *
 * @author wenchangwei
 * @since 7:27 下午 2020/11/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfo implements Serializable {

    private static final long serialVersionUID = 1140323733091613531L;

    /**
     * 用户名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

}
