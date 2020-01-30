package com.hanyi.demo.common.design.observe;

import com.hanyi.demo.common.design.observe.impl.ObserverImpl;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.observe ObserverTest
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 20:05
 * @Version: 1.0
 */
public class ObserverTest {

    public static void main(String[] args) {

        // 目标对象
        RealObserver realObserver = new RealObserver();
        // 创建多个观察者
        ObserverImpl obs1 = new ObserverImpl();
        ObserverImpl obs2 = new ObserverImpl();
        ObserverImpl obs3 = new ObserverImpl();
        // 注册到观察队列中
        realObserver.registerObserver(obs1);
        realObserver.registerObserver(obs2);
        realObserver.registerObserver(obs3);
        // 改变State状态
        realObserver.setState(300);
        System.out.println(obs1.getMyState());
        System.out.println(obs2.getMyState());
        System.out.println(obs3.getMyState());
        // 改变State状态
        realObserver.setState(400);
        System.out.println(obs1.getMyState());
        System.out.println(obs2.getMyState());
        System.out.println(obs3.getMyState());

    }

}
