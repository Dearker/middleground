package com.hanyi.daily.load;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * 系统Api测试类，
 * System.load()/loadLibrary和RunTime.load/loadLibrary都是用来装载库文件，不论是JNI库文件还是非JNI库文件，即jar包
 *
 * @author wcwei@iflytek.com
 * @since 2021-06-11 17:09
 */
public class SystemTest {

    /**
     * 获取系统属性
     */
    @Test
    public void systemEnvTest() {
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k, v) -> System.out.println(k + " || " + v));
    }

    /**
     * 系统属性测试
     * <p>
     * os.arch	操作系统的架构
     * os.version	操作系统的版本
     * file.separator	文件分隔符（在 UNIX 系统中是“/”）
     * path.separator	路径分隔符（在 UNIX 系统中是“:”）
     * line.separator	行分隔符（在 UNIX 系统中是“/n”）
     * user.name	用户的账户名称
     * user.home	用户的主目录
     * user.dir	用户的当前工作目录
     */
    @Test
    public void systemPropertiesTest() {
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + " || " + v));

        System.out.println("-------------------");
        System.out.println(System.getProperty("java.version"));
    }

    /**
     * 如果两个对象的identityHashCode值相等，则两个对象绝对是同一个对象；
     * 对象的hashCode()计算出来的值不同的属性可能为同一个
     */
    @Test
    public void systemIdentityHashCodeTest() {
        System.out.println(System.identityHashCode("1111"));
    }

    /**
     * 系统退出测试
     * 0 正常退出，程序正常执行结束退出，Java GC进行垃圾回收，直接退出
     * 1 是非正常退出，就是说无论程序正在执行与否，都退出
     */
    @Test
    public void systemExitTest() {
        System.out.println("111111");
        //正常退出程序
        System.exit(0);
        //下面代码不会执行
        System.out.println("111111222");
    }

    /**
     * 系统行分隔符测试
     * 在UNIX系统上，返回"\n" ; 在Microsoft Windows系统上，返回"\r\n"
     */
    @Test
    public void systemLineSeparatorTest() {
        System.out.println(System.lineSeparator());
    }

    /**
     * 运行时内存测试
     */
    @Test
    public void runTimeMemoryTest() {
        Runtime runtime = Runtime.getRuntime();

        System.out.println("Java虚拟机中的可用内存量:" + runtime.freeMemory());
        System.out.println("Java虚拟机中的内存总量:" + runtime.totalMemory());
        System.out.println("Java虚拟机将尝试使用的最大内存量:" + runtime.maxMemory());
    }

    /**
     * 运行时执行命令测试
     *
     * @throws IOException 异常
     */
    @Test
    public void runTimeExecTest() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("ls -l");

        System.out.println(process.getInputStream());
    }

}
