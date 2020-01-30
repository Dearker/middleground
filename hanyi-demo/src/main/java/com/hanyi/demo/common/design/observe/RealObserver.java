package com.hanyi.demo.common.design.observe;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.observe RealObserver
 * @Author: weiwenchang
 * @Description: 具体观察者对象的实现
 * @CreateDate: 2020-01-23 20:03
 * @Version: 1.0
 */
public class RealObserver extends Subjecct {

    /**
     * 被观察对象的属性
     */
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        //主题对象(目标对象)值发生改变
        this.notifyAllObserver();
    }

}
