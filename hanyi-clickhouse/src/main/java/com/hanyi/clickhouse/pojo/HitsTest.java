package com.hanyi.clickhouse.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 点击测试
 *
 * @author weiwen
 * @date 2021/07/19
 */
@Data
@TableName(value ="hits_test")
public class HitsTest implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 看身份证
     */
    @TableId
    private Integer watchId;

    /**
     * java支持
     */
    private Integer javaEnable;

    /**
     * 标题
     */
    private String title;

    /**
     * 很好的活动
     */
    private Integer goodEvent;

    /**
     * 事件时间
     */
    private LocalDateTime eventTime;

}