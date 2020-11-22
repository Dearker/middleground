package com.hanyi.framework.model.response;

import com.hanyi.framework.enums.ResultCode;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: middleground com.hanyi.daily.pojo.vo ResponseResult
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-05 15:54
 * @Version: 1.0
 */
@Data
@Builder
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 3469848541768941054L;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;


    public static ResponseResult success() {
        return createResult(ResultCode.SUCCESS);
    }

    public static ResponseResult success(Object object) {
        return createResult(ResultCode.SUCCESS, object);
    }

    public static ResponseResult failure(ResultCode resultCode) {
        return createResult(resultCode);
    }

    public static ResponseResult failure(ResultCode resultCode, Object object) {
        return createResult(resultCode, object);
    }


    private static ResponseResult createResult(ResultCode resultCode) {
        return createResult(resultCode, null);
    }

    private static ResponseResult createResult(ResultCode resultCode, Object object) {
        return ResponseResult.builder().code(resultCode.getCode()).message(resultCode.getMessage()).data(object).build();
    }

}
