package com.hanyi.rocket.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户信息实体类
 * </p>
 *
 * @author wenchangwei
 * @since 9:48 下午 2020/12/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 2146445718358709568L;

    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户年龄
     */
    private Integer userAge;

}
