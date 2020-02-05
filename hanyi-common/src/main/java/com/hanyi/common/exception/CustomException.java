package com.hanyi.common.exception;

import com.hanyi.framework.enums.ResultCode;
import lombok.Getter;

/**
 * 自定义异常类型
 * @author hanyi
 * @version 1.0
 * @create 2019-10-15
 **/
@Getter
public class CustomException extends RuntimeException {

    /**
     * 错误代码
     */
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    

}
