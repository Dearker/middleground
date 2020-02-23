package com.hanyi.demo.common.design.template;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.template
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 14:25
 * @Version: 1.0
 */
abstract class AbstractBankTemplateMethod {

    abstract void takeNumber();

    abstract void transact();

    abstract void evaluate();

    void process(){
        takeNumber();
        transact();
        evaluate();
    }

}
