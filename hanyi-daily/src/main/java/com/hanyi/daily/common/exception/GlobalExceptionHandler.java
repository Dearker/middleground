package com.hanyi.daily.common.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @PackAge: middleground com.hanyi.daily.common.exception
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-01 16:43
 * @Version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 忽略参数异常处理器
     *
     * @param e 忽略参数异常
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error("", e);
        return ResponseResult.failure(ResultCode.PARAM_IS_INVALID, "请求参数 " + e.getParameterName() + " 不能为空");
    }

    /**
     * 缺少请求体异常处理器  常用
     *
     * @param e 缺少请求体异常
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error("GlobalExceptionHandler of parameterBodyMissingExceptionHandler have : ", e);
        return ResponseResult.failure(ResultCode.PARAM_IS_INVALID, "参数体不能为空");
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
        log.error("{}", e.getMessage());
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<FieldError> errors = exceptions.getFieldErrors();
            if (CollUtil.isNotEmpty(errors)) {
                // 列出全部错误参数
                Map<String, String> errorMap = errors.stream().filter(s -> StrUtil.isNotBlank(s.getDefaultMessage()))
                        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
                return ResponseResult.failure(ResultCode.PARAM_IS_INVALID, errorMap);
            }
        }
        return ResponseResult.failure(ResultCode.PARAM_IS_INVALID);
    }

}
