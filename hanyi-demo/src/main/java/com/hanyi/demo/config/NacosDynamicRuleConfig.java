package com.hanyi.demo.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hanyi.demo.component.NacosPropertyComponent;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.demo.config
 * @Author: weiwenchang
 * @Description: sentinel使用nacos作为动态规则数据源
 * @CreateDate: 2020-02-16 21:12
 * @Version: 1.0
 */
@Configuration
public class NacosDynamicRuleConfig {

    @Resource
    private NacosPropertyComponent nacosPropertyComponent;

    @PostConstruct
    private void doInit() {
        loadRules();
    }

    private void loadRules() {

        String remoteAddress = nacosPropertyComponent.getRemoteAddress();
        String groupId = nacosPropertyComponent.getGroupId();
        String dataId = nacosPropertyComponent.getDataId();

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }

}
