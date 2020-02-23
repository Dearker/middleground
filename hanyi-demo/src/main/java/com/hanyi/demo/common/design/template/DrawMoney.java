package com.hanyi.demo.common.design.template;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.template DrawMoney
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 14:27
 * @Version: 1.0
 */
class DrawMoney extends AbstractBankTemplateMethod {

    @Override
    void takeNumber() {
        System.out.println("取号排队。。");
    }

    @Override
    void transact() {
        System.out.println("我要取款");
    }

    @Override
    void evaluate() {
        System.out.println("反馈评价..");
    }

}
