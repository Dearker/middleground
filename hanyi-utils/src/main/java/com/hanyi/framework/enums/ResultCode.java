package com.hanyi.framework.enums;

import lombok.Getter;

/**
 * @ClassName: middleground com.hanyi.framework.enums ResultCode
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-05 17:02
 * @Version: 1.0
 */
@Getter
public enum ResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(200,"成功"),
    FAILED(1001,"失败"),
    PARAM_IS_INVALID(1002,"参数无效"),
    UPLOAD_FAILED(1003,"上传失败"),
    FAILED_TO_DELETE(1004,"删除失败");

    private final Integer code;

    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
