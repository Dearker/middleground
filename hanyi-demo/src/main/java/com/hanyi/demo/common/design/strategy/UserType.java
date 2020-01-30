package com.hanyi.demo.common.design.strategy;


/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy UserType
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:26
 * @Version: 1.0
 */
public enum UserType {

    SILVER_VIP(1,"白银会员 优惠50元"),
    GOLD_VIP(2,"黄金会员 8折"),
    PLATINUM_VIP(3,"白金会员 优惠50元，再打7折"),
    ORDINARY_VIP(4,"普通会员 不打折");

    private int type;
    private String desc;

    UserType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
