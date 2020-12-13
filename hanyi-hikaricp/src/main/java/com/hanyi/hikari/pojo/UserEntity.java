package com.hanyi.hikari.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author wenchangwei
 * @since 9:33 上午 2020/12/12
 */
@Data
@TableName("tb_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 8818154772113364086L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 出生日期
     */
    private String birthday;

}
