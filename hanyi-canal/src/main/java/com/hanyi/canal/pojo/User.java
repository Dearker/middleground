package com.hanyi.canal.pojo;

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
 * 用户实体类
 * </p>
 *
 * @author wenchangwei
 * @since 10:41 下午 2020/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = -5140132734893621522L;

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
