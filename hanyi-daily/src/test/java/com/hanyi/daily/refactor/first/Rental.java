package com.hanyi.daily.refactor.first;

import lombok.Data;

/**
 * <p>
 * 租赁
 * </p>
 *
 * @author wenchangwei
 * @since 5:34 下午 2021/1/16
 */
@Data
public class Rental {

    private Movie movie; // 影片
    private int daysRented; // 租期

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public double getCharge() {
        double thisAmount = 0;
        // determine amounts for each line
        switch (getMovie().getPriceCode()) { // 取得影片出租价格
            case Movie.REGULAR: // 普通片
                thisAmount += 2;
                if (getDaysRented() > 2)
                    thisAmount += (getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE: // 新片
                thisAmount += getDaysRented() * 3;
                break;
            case Movie.CHILDRENS: // 儿童。
                thisAmount += 1.5;
                if (getDaysRented() > 3)
                    thisAmount += (getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
    }

}
