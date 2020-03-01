package com.hanyi.daily.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private Integer userId;

    @NotBlank(message = "用户名称不能为空")
    private String userName;

    @NotNull(message = "用户年龄不能为空")
    private Integer userAge;

}
