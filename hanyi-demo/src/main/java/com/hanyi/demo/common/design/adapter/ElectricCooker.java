package com.hanyi.demo.common.design.adapter;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.adapter ElectricCooker
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 15:37
 * @Version: 1.0
 */
public class ElectricCooker {

    private JP110VInterface jp110VInterface;

    ElectricCooker(JP110VInterface jp110VInterface){
        this.jp110VInterface=jp110VInterface;
    }

    public void cook(){
        jp110VInterface.connect();
        System.out.println("开始做饭了..");
    }

}
