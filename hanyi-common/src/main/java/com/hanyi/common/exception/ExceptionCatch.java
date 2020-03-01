package com.hanyi.common.exception;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.ImmutableMap;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 统一异常捕获类
 * @author hanyi
 * @version 1.0
 * @create 2019-10-15
 **/
@RestControllerAdvice
@Slf4j
public class ExceptionCatch {

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
        log.error("catch exception:{}",customException.getMessage());
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
        log.error("catch exception:{}",exception.getMessage());
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

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return ResponseInfo
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error("", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (CollUtil.isNotEmpty(errors)) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return ResponseResult.failure(ResultCode.PARAM_IS_INVALID, fieldError.getDefaultMessage());
            }
        }
        return ResponseResult.failure(ResultCode.PARAM_IS_INVALID);
    }


    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class,ResultCode.PARAM_IS_INVALID);
    }
}
