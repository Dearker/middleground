package com.hanyi.sharding.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 4958893400046869582L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    private String name;

    /**
     * 用户年龄
     */
    @NotNull(message = "用户年龄不能为空")
    private Integer age;

}
