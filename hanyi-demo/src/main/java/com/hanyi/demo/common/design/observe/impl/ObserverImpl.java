package com.hanyi.demo.common.design.observe.impl;

import com.hanyi.demo.common.design.observe.Observer;
import com.hanyi.demo.common.design.observe.RealObserver;
import com.hanyi.demo.common.design.observe.Subjecct;
import lombok.Data;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.observe.impl ObserverImpl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 20:09
 * @Version: 1.0
 */
@Data
public class ObserverImpl implements Observer {

    /**
     * 需要和目标对象的state值保持一致.
     */
    private int myState;

    @Override
    public void update(Subjecct subjecct) {
        myState = ((RealObserver) subjecct).getState();
    }

}
