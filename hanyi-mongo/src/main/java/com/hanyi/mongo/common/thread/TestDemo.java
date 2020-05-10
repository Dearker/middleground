package com.hanyi.mongo.common.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.common.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 22:27
 * @Version: 1.0
 */
public class TestDemo {

    public static void main(String[] args) {

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);
        integerList.add(6);

        for (int i = 0; i < 3; i++) {
            System.out.println(integerList.subList(i*2,i*2+2));
        }

    }

}
