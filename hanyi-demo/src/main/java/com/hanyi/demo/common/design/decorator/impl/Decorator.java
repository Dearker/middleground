package com.hanyi.demo.common.design.decorator.impl;

import com.hanyi.demo.common.design.decorator.People;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.decorator.impl Decorator
 * @Author: weiwenchang
 * @Description: 装饰者
 * @CreateDate: 2020-01-23 19:26
 * @Version: 1.0
 */
public abstract class Decorator implements People {

    private People people;

    public Decorator(People people) {
        this.people = people;
    }

    @Override
    public void drinking() {
        people.drinking();
    }

    @Override
    public void driving() {
        people.driving();
    }
}
