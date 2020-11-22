package com.hanyi.framework.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 业务异常
 * </p>
 *
 * @author wenchangwei
 * @since 7:39 下午 2020/11/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException{

    /**
     * 错误信息
     */
    private String errorMsg;

    public BizException() {
        super();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
    }
}
