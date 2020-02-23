package com.hanyi.demo.common.design.template;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.template Number
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 15:17
 * @Version: 1.0
 */
class Number extends AbstractBankTemplateMethod {

    @Override
    void takeNumber() {
        System.out.println("1");
    }

    @Override
    void transact() {
        System.out.println("2");
    }

    @Override
    void evaluate() {
        System.out.println("3");
    }
}
