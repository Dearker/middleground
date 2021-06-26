package com.hanyi.dynamic.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户详情实体类
 *
 * @author weiwen
 * @date 2021/06/26
 */
@Data
@TableName(value ="user_info")
public class UserInfo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}