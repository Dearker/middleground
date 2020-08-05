package com.hanyi.ordinary.export;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * 导出抽象类
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 17:16 2020/8/5
 */
@Slf4j
public abstract class AbstractExport {

    @Value("${server.port}")
    private int serverPort;

    public void buildData() {

        int queryCount = this.queryCount();

        int i = queryCount + serverPort;
        log.info("buildData is {}", i);
    }

    /**
     * 查询总数
     *
     * @return 返回总数结果
     */
    protected abstract int queryCount();

}
