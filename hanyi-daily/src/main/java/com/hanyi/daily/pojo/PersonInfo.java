package com.hanyi.daily.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户详情实体类
 *
 * @author wcwei@iflytek.com
 * @since 2020-12-25 17:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonInfo extends Person {

    private Integer age;

    private LocalDateTime createTime;

}
