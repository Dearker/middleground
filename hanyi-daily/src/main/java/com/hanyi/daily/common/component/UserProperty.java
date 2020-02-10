package com.hanyi.daily.common.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @PackAgeName: middleground com.hanyi.daily.common.component
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-07 14:56
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "keji")
public class UserProperty {

    private Integer id;
    private String name;
    private Integer age;

}


