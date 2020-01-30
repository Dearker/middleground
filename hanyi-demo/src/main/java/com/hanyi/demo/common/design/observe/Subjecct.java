package com.hanyi.demo.common.design.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.observe Subjecct
 * @Author: weiwenchang
 * @Description: 观察对象的父类
 * @CreateDate: 2020-01-23 20:00
 * @Version: 1.0
 */
public class Subjecct {

    /**
     * 观察者的存储集合
     */
    private List<Observer> list = new ArrayList<>();

    /**
     * 注册观察者方法
     *
     * @param obs
     */
    public void registerObserver(Observer obs) {
        list.add(obs);
    }

    /**
     * 删除观察者方法
     *
     * @param obs
     */
    public void removeObserver(Observer obs) {
        list.remove(obs);
        this.notifyAllObserver();
    }

    /**
     * 通知所有的观察者更新
     */
    public void notifyAllObserver() {
        list.forEach(observer -> observer.update(this));
    }

}
