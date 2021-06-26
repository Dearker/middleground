package com.hanyi.dynamic.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据库配置类
 *
 * @author weiwen
 */
@Data
@TableName(value ="data_base_config")
public class DataBaseConfigInfo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 数据库类型
     */
    private String dataBaseType;

    /**
     * 数据库连接
     */
    private String dataUrl;

    /**
     * 驱动名称
     */
    private String driverClassName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否启用,0表示启用，1表示未启用
     */
    private Integer enableStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}