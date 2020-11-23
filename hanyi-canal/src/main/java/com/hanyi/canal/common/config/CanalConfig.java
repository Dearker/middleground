package com.hanyi.canal.common.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.hanyi.canal.common.property.CanalProperty;
import com.hanyi.framework.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * <p>
 * canal配置类
 * </p>
 *
 * @author wenchangwei
 * @since 7:26 下午 2020/11/22
 */
@Slf4j
@Configuration
public class CanalConfig implements DisposableBean {

    private CanalConnector canalConnector;

    @Bean
    public CanalConnector canalConnector(CanalProperty canalProperty) {
        if(Objects.isNull(canalProperty)){
            throw new BizException("error ,appConfig is null");
        }
        String ip = StrUtil.isBlank(canalProperty.getIp()) ? AddressUtils.getHostIp() : canalProperty.getIp();
        // 创建链接
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, canalProperty.getPort());
        canalConnector = CanalConnectors.newSingleConnector(inetSocketAddress,
                canalProperty.getDestination(), canalProperty.getUsername(), canalProperty.getPassport());

        // 链接canal
        canalConnector.connect();
        // 指定filter，格式 {database}.{table}，这里不做过滤，过滤操作留给用户
        canalConnector.subscribe(canalProperty.getSubscribe());
        // 回滚寻找上次中断的位置
        canalConnector.rollback();
        log.info("canal客户端启动成功");
        return canalConnector;
    }

    /**
     * 当bean销毁时断开canal的链接
     */
    @Override
    public void destroy() {
        if (Objects.nonNull(canalConnector)) {
            canalConnector.disconnect();
        }
    }
}
