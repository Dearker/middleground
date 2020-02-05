package com.hanyi.daily.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: middleground com.hanyi.daily.pojo User
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-05 09:47
 * @Version: 1.0
 */
@Data
@Builder
public class User implements Serializable {

    private Integer userId;
    private String userName;
    private Integer userAge;

}
