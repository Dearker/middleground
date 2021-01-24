package com.hanyi.daily.refactor.first;

import lombok.Data;

/**
 * <p>
 * 影片
 * </p>
 *
 * @author wenchangwei
 * @since 5:33 下午 2021/1/16
 */
@Data
public class Movie {

    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;
    private String title;
    private int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

}
