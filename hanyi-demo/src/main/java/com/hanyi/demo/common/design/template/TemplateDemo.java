package com.hanyi.demo.common.design.template;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.template TemplateDemo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 14:29
 * @Version: 1.0
 */
public class TemplateDemo {

    public static void main(String[] args) {

        AbstractBankTemplateMethod bankTemplate = new DrawMoney();
        bankTemplate.process();

        AbstractBankTemplateMethod abstractBankTemplateMethod = new Number();
        abstractBankTemplateMethod.process();

        AbstractBankTemplateMethod b = new AbstractBankTemplateMethod() {
            @Override
            void takeNumber() {
                System.out.println("111");
            }

            @Override
            void transact() {
                System.out.println("222");
            }

            @Override
            void evaluate() {
                System.out.println("333");
            }
        };
        b.process();
    }

}
