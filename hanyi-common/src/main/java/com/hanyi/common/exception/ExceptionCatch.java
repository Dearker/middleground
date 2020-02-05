package com.hanyi.common.exception;

import com.google.common.collect.ImmutableMap;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常捕获类
 * @author hanyi
 * @version 1.0
 * @create 2019-10-15
 **/
@RestControllerAdvice
public class ExceptionCatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    /**
     * 定义map，配置异常类型所对应的错误代码
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    /**
     * 定义map的builder对象，去构建ImmutableMap
     */
    private static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    /**
     * 捕获CustomException此类异常
     * @param customException
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResponseResult customException(CustomException customException){
        customException.printStackTrace();
        //记录日志
        LOGGER.error("catch exception:{}",customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return ResponseResult.failure(resultCode);
    }

    /**
     * 捕获Exception此类异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception exception){
        exception.printStackTrace();
        //记录日志
        LOGGER.error("catch exception:{}",exception.getMessage());
        if(EXCEPTIONS == null){
            //EXCEPTIONS构建成功
            EXCEPTIONS = builder.build();
        }
        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if(resultCode !=null){
            return ResponseResult.failure(resultCode);
        }else{
            //返回99999异常
            return ResponseResult.failure(ResultCode.FAILED);
        }
    }

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class,ResultCode.PARAM_IS_INVALID);
    }
}
