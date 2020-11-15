package com.hanyi.session.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author wenchangwei
 * @since 11:28 下午 2020/11/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 8745305294693764174L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

}
