package com.hanyi.daily.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ClassName: middleground com.hanyi.daily.pojo ValidationEntity
 * @Author: weiwenchang
 * @Description: 校验实体类
 * @CreateDate: 2020-02-04 21:41
 * @Version: 1.0
 *
 * @Null 被注释的元素必须为null
 * @NotNull 被注释的元素不能为null
 * @AssertTrue 被注释的元素必须为true
 * @AssertFalse 被注释的元素必须为false
 * @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @Size(max,min) 被注释的元素的大小必须在指定的范围内。
 * @Digits(integer,fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
 * @Past 被注释的元素必须是一个过去的日期
 * @Future 被注释的元素必须是一个将来的日期
 * @Pattern(value) 被注释的元素必须符合指定的正则表达式。
 * @Email 被注释的元素必须是电子邮件地址
 * @Length 被注释的字符串的大小必须在指定的范围内
 * @NotEmpty 被注释的字符串必须非空
 * @Range 被注释的元素必须在合适的范围内
 */
@Data
public class ValidationEntity implements Serializable {

    /**
     * 用户名
     */
    @NotNull
    private String username;

    /**
     * 密码
     */
    @Size(min = 6, max = 25, message = "密码长度要求6到25之间")
    private String password;

    /**
     * 姓名(昵称)
     */
    @NotNull
    private String nickname;

    /**
     * 邮箱
     */
    @Email(message="邮箱格式不正确")
    private String email;

    /**
     * 手机号
     */
    @Pattern(regexp="^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$",message="手机格式不正确")
    private String mobile;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp gmtModified;

}
