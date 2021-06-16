package com.hanyi.daily.load;

import cn.hutool.core.util.RuntimeUtil;
import com.hanyi.daily.property.Book;
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
     * 如果两个对象的identityHashCode值相等，则两个对象绝对是同一个对象，即调用Object的hashcode()方法；
     * 返回与默认方法hashCode（）返回的给定对象相同的哈希码，无论给定对象的类是否覆盖了hashCode（）。 空引用的哈希码为零。
     * 如果在web请求中传入的为String类型，则每次都会创建一个新的String，即hashCode()值每次都不一样.
     * 如果想每次相同的值计算出来的结果相同，使用HashUtil.jsHash()方法，如果传入的参数为对象的toString()则需要先重写其toString()
     * 不同的两个对象的hashCode()计算出来的值可能相同；如果在程序内部自己使用可适当的使用System.identityHashCode()
     */
    @Test
    public void systemIdentityHashCodeTest() {
        String a = "重地";
        String b = "通话";
        //相同
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());

        //不同
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode("重地"));
        System.out.println(System.identityHashCode(b));

        System.out.println("-------------------");

        Book book = new Book("重地", 1);
        Book book1 = new Book("通话", 1);

        //相同
        System.out.println(book.hashCode() + " || " + book1.hashCode());
        //不同
        System.out.println(System.identityHashCode(book) + " || " + System.identityHashCode(book1));
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

        Process exec = RuntimeUtil.exec("ls -l");
        System.out.println(exec);

        String str = RuntimeUtil.execForStr("ipconfig");
        System.out.println(str);
    }

}
