package com.hanyi.demo.common.design.decorator.impl;

import com.hanyi.demo.common.design.decorator.People;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.decorator.impl DecoratorXMan
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 19:32
 * @Version: 1.0
 */
public class DecoratorXMan extends Decorator{

    public DecoratorXMan(People people) {
        super(people);
    }

    private void howMuch(){
        System.out.println("**********喝一箱**********");
    }
    private void where(){
        System.out.println("**********往城市边缘瞎开**********");
    }

    @Override
    public void drinking() {
        super.drinking();
        howMuch();
    }

    @Override
    public void driving() {
        super.driving();
        where();
    }
}
