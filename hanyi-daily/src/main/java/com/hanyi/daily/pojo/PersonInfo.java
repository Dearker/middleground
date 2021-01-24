package com.hanyi.daily.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户详情实体类
 *
 * @author wcwei@iflytek.com
 * @since 2020-12-25 17:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonInfo extends Person {

    private Integer age;

    private LocalDateTime createTime;

}
