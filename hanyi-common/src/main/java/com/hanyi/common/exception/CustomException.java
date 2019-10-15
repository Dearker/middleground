package com.hanyi.common.exception;

import com.hanyi.common.model.response.ResultCode;

/**
 * 自定义异常类型
 * @author hanyi
 * @version 1.0
 * @create 2019-10-15
 **/
public class CustomException extends RuntimeException {

    /**
     * 错误代码
     */
    ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return resultCode;
    }


}
