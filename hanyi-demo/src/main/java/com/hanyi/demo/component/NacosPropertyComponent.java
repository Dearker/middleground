package com.hanyi.demo.component;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @PackAge: middleground com.hanyi.demo.component
 * @Author: weiwenchang
 * @Description: nacos配置信息
 * @CreateDate: 2020-02-17 10:44
 * @Version: 1.0
 */
@Component
public class NacosPropertyComponent {

    @Resource
    private NacosConfigProperties nacosConfigProperties;

    @Value("${spring.application.name}")
    private String applicationName;

    public String getRemoteAddress(){
        return StrUtil.split(nacosConfigProperties.getServerAddr(),":")[0];
    }

    public String getGroupId(){
        return nacosConfigProperties.getGroup();
    }

    public String getDataId(){
        return applicationName +"."+ nacosConfigProperties.getFileExtension();
    }

}
