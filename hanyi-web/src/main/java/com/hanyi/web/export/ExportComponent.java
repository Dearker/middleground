package com.hanyi.web.export;

import org.springframework.stereotype.Component;

/**
 * <p>
 * 导出组件类
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 17:21 2020/8/5
 */
@Component
public class ExportComponent extends AbstractExport {

    @Override
    protected int queryCount() {
        return 100;
    }
}
