package com.hanyi.hikari.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户详情实体类
 * </p>
 *
 * @author wenchangwei
 * @since 9:35 上午 2020/12/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user_info")
public class UserInfoEntity implements Serializable {

    private static final long serialVersionUID = -8461721949267964499L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 从事领域编码
     */
    private String infoCode;

}
