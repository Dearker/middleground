package com.hanyi.daily.common.enums;

import lombok.Getter;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:46 下午 2021/1/4
 */
@Getter
public enum CodeEnum {

    /**
     * 第一
     */
    ONE(1,"哈士奇"),
    TWO(2,"柯基"),
    THREE(3,"柴犬");

    private final int code;

    private final String codeName;

    CodeEnum(int code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

}
