package com.hanyi.daily.load;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.daily.load
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-10 10:47
 * @Version: 1.0
 */
public class CollectionTest {

    /**
     * list 和 string的转换
     */
    @Test
    public void stringAndListConvertTest() {

        List<String> stringList = Arrays.asList("111", "222", "333");
        String join = StrUtil.join(",", stringList);

        List<String> strings = Arrays.asList(StrUtil.split(join, ","));
        strings.forEach(System.out::println);
    }

}
