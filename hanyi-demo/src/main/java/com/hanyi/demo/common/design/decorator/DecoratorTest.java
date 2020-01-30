package com.hanyi.demo.common.design.decorator;

import com.hanyi.demo.common.design.decorator.impl.*;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.decorator DecoratorTest
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 16:58
 * @Version: 1.0
 */
public class DecoratorTest {

    public static void main(String[] args) {

        People man = new Man();
        Decorator decoratorMan = new DecoratorMan(man);
        //定义一个真男人
        Decorator decoratorXMan = new DecoratorXMan(decoratorMan);
        decoratorXMan.drinking();
        decoratorXMan.driving();

    }

}
