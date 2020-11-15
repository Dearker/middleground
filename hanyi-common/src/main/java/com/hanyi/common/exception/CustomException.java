package com.hanyi.common.exception;

import com.hanyi.framework.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常类型
 * @author hanyi
 * @version 1.0
 * @create 2019-10-15
 **/
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    /**
     * 错误代码
     */
    private final ResultCode resultCode;

}
