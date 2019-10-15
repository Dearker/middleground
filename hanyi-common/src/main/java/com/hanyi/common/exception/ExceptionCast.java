package com.hanyi.common.exception;

import com.hanyi.common.model.response.ResultCode;

/**
 * @author hanyi
 * @version 1.0
 * @create 2019-10-15
 **/
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
