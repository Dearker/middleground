package com.hanyi.demo.common.design.decorator.impl;

import com.hanyi.demo.common.design.decorator.People;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.decorator.impl Man
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 19:25
 * @Version: 1.0
 */
public class Man implements People {

    @Override
    public void drinking() {
        System.out.println("**********喝酒**********");
    }

    @Override
    public void driving() {
        System.out.println("**********开车**********");
    }
}
