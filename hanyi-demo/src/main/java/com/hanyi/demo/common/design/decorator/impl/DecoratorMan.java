package com.hanyi.demo.common.design.decorator.impl;

import com.hanyi.demo.common.design.decorator.People;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.decorator.impl DecoratorMan
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 19:28
 * @Version: 1.0
 */
public class DecoratorMan extends Decorator{

    public DecoratorMan(People people) {
        super(people);
    }

    private void lookingForWine(){
        System.out.println("**********找到啤酒一箱**********");
    }
    private void lookingForCar(){
        System.out.println("**********找到车一辆**********");
    }

    /**
     * 喝酒
     */
    @Override
    public void drinking() {
        lookingForWine();
        super.drinking();
    }

    /**
     * 开车
     */
    @Override
    public void driving() {
        lookingForCar();
        super.driving();
    }

}
