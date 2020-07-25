package com.hanyi.ordinary.common.configuration;

import com.hanyi.ordinary.pojo.ThreadPoolBean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 全局配置类
 * </p>
 *
 * @author wenchangwei
 * @since 11:07 下午 2020/7/24
 */
@Configuration
public class GlobalConfiguration {

    public ThreadPoolBean threadPoolBean(){
        return new ThreadPoolBean();
    }

}
