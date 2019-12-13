package com.hanyi.demo;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.setting.dialect.Props;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

/**
 * @ClassName: middleground com.hanyi.demo ClassLoaderTest
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-12-12 20:05
 * @Version: 1.0
 */
public class ClassLoaderTest {

    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    /**
     * 通过类名称获取包名
     */
    @Test
    public void ClassLoader() {
        String aPackage = ClassUtil.getPackage(ClassLoaderTest.class);
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(aPackage, SpringBootApplication.class);
        classes.forEach(s -> System.out.println(ClassUtil.getPackage(s)));

    }

    @Test
    public void ExecTest(){
        Process exec = RuntimeUtil.exec("ls -l");
        String str = RuntimeUtil.execForStr("ipconfig");

    }

    @Test
    public void IdTest(){
        long l = snowflake.nextId();

        Console.log("This is Console log for {}.", l);
        Props props = new Props("test.properties");
    }

}
