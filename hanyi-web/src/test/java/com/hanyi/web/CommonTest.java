package com.hanyi.web;

import com.hanyi.web.service.DefaultFunctionService;
import com.hanyi.web.service.impl.EmptyFunctionServiceImpl;
import com.hanyi.web.service.impl.FunctionServiceImpl;
import org.junit.Test;

/**
 * <p>
 * 通用测试类
 * </p>
 *
 * @author wenchangwei
 * @since 6:45 下午 2021/4/17
 */
public class CommonTest {

    /**
     * 默认接口实现测试，
     * 实现类中没有重写接口中的方法，则会使用接口中默认的实现；
     * 实现类中重写了接口中的方法，则会使用实现类中的方法。
     */
    @Test
    public void defaultInterfaceTest(){
        DefaultFunctionService emptyFunctionService = new EmptyFunctionServiceImpl();
        System.out.println(emptyFunctionService.function(null));
        System.out.println(emptyFunctionService.compute(null));

        System.out.println("-------------------------------------");
        DefaultFunctionService functionService = new FunctionServiceImpl();

        System.out.println(functionService.function(null));
        System.out.println(functionService.compute(null));
    }

}
