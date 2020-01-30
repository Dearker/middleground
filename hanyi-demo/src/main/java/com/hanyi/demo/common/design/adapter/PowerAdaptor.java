package com.hanyi.demo.common.design.adapter;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.adapter PowerAdaptor
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 15:38
 * @Version: 1.0
 */
public class PowerAdaptor implements JP110VInterface{

    private CN220VInterface cn220VInterface;

    public PowerAdaptor(CN220VInterface cn220VInterface) {
        this.cn220VInterface = cn220VInterface;
    }

    @Override
    public void connect() {
        cn220VInterface.connect();
    }
}
