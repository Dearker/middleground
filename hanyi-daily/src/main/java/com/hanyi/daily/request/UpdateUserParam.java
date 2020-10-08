package com.hanyi.daily.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:02 上午 2020/10/1
 */
@Data
@AllArgsConstructor
public class UpdateUserParam implements Serializable {

    private static final long serialVersionUID = -6409004027230166673L;

    /**
     * 用户id
     */
    private List<Integer> userIdList;

    /**
     * 姓名
     */
    private String userName;

}
