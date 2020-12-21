package com.hanyi.daily.load;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hanyi.daily.pojo.User;
import com.hanyi.daily.property.Book;
import com.hanyi.daily.property.Person;
import org.junit.Test;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @PackAge: middleground com.hanyi.daily.load
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-01 10:55
 * @Version: 1.0
 */
public class LoaderTest {

    /**
     * 读取本地json文件，Linux环境会报错，找不到文件路径
     */
    @Test
    public void readJSONFile() {
        //获取当前运行环境
        Object o = System.getProperties().get("os.name");
        System.out.println(o);

        String pathname = this.getClass().getClassLoader().getResource("static/json/appType.json").getPath();
        System.out.println(pathname);
        File file = new File(pathname);
        JSONObject jsonObject = JSONUtil.readJSONObject(file, Charset.defaultCharset());
        System.out.println(jsonObject);

    }

    /**
     * 获取所有的系统属性
     */
    @Test
    public void systemPropertiesTest() {
        System.getProperties().list(System.out);
    }

    /**
     * 获取CPU核心数
     * <p>
     * CPU 密集型：线程数量 = cpu核心数量
     * IO 密集型：线程数量 = cpu核心数量 * 2
     */
    @Test
    public void getCpuCountTest() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println(availableProcessors);
    }

    /**
     * 根据字节码获取其对应的对象
     */
    @Test
    public void genericTypeTest() {
        Person person = this.getObject(Person.class);
        Book book = this.getObject(Book.class);
    }

    private <T> T getObject(Class<T> clazz) {
        return ReflectUtil.newInstance(clazz);
    }

    /**
     * 方法中的参数为接口
     */
    @Test
    public void interfaceParamTest() {

        User person = new User();
        person.setUserAge(12);
        List<Serializable> common = this.buildParams(person);

        System.out.println(common);
    }

    private List<Serializable> buildParams(Serializable serializable) {
        List<Serializable> list = new ArrayList<>();
        list.add(serializable);
        return list;
    }

    /**
     * finally 测试
     */
    @Test
    public void finallyTest() {
        System.out.println(getInt());
        System.out.println(getInt2());
    }

    private int getInt() {
        try {
            return 1;
        } finally {
            System.out.println("1111");
        }
    }

    private int getInt2() {
        try {
            return 2;
        } finally {
            System.out.println("2222");
        }
    }

    /**
     * 将相同对象的不同属性进行复制
     */
    @Test
    public void changeUser() {

        User user = new User(1, "柯基", 20);

        User u = new User();
        //u.setUserId(1);
        //u.setUserName("哈士奇");
        u.setUserAge(30);

        Map<String, Object> objectMap = BeanUtil.beanToMap(u);
        Map<String, Object> bean = BeanUtil.beanToMap(user);
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            if (Objects.isNull(entry.getValue())) {
                entry.setValue(bean.get(entry.getKey()));
            }
        }
        User mapToBean = BeanUtil.toBean(objectMap, User.class);

        System.out.println(mapToBean);
    }

    @Test
    public void attributeToMap() {

        NamedThreadFactory namedThreadFactory = new NamedThreadFactory("thread-pool-business", false);
        ThreadPoolExecutor threadPoolExecutor = ExecutorBuilder.create().setThreadFactory(namedThreadFactory)
                .setCorePoolSize(5)
                .setMaxPoolSize(5).build();

        List<Object> objectList = new ArrayList<>();
        objectList.add(threadPoolExecutor.getCorePoolSize());
        objectList.add(threadPoolExecutor.getMaximumPoolSize());
        objectList.add(threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
        objectList.add(threadPoolExecutor.getQueue());
        objectList.add(threadPoolExecutor.getThreadFactory());
        objectList.add(threadPoolExecutor.getRejectedExecutionHandler());

        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(threadPoolExecutor);
        //stringObjectMap.forEach((k, v) -> System.out.println(k + "||" + v));

        objectList.forEach(System.out::println);

    }

    /**
     * ip转long类型
     */
    @Test
    public void ipTest() {
        long ipv4ToLong = NetUtil.ipv4ToLong(NetUtil.LOCAL_IP);
        System.out.println(ipv4ToLong);
    }

    /**
     * String的split方法时，传入的分隔字符串是正则表达式，则部分关键字（比如.[]()|等需要转义
     * 如果去掉\\则返回的结果不是预期的效果
     */
    @Test
    public void splitTest() {
        String[] split = "a.ab.abc".split("\\.");
        //[a, ab, abc]
        System.out.println(Arrays.toString(split));
        String[] split3 = "a|ab|abc".split("\\|");
        //[a, ab, abc]
        System.out.println(Arrays.toString(split3));
    }

    /**
     * 用于存储一组任务的耗时时间，并一次性打印对比
     *
     * @throws InterruptedException 线程异常
     */
    @Test
    public void stopWatchTest() throws InterruptedException {

        StopWatch stopWatch = new StopWatch("任务名称");

        // 任务1
        stopWatch.start("任务一");
        TimeUnit.SECONDS.sleep(1);
        stopWatch.stop();

        // 任务2
        stopWatch.start("任务一");
        TimeUnit.SECONDS.sleep(2);
        stopWatch.stop();

        // 打印出耗时
        Console.log(stopWatch.prettyPrint());
    }

    /**
     * 将字符串转换成base64编码格式
     */
    @Test
    public void base64Test() {
        String root = Base64.encode("root");
        //cm9vdA==
        System.out.println(root);
    }

    /**
     * 测试@Accessors(chain = true)注解
     */
    @Test
    public void accessorsTest() {
        User user = new User().setUserId(1).setUserName("哈士奇").setUserAge(12);
        System.out.println(user);
    }

    /**
     * StringJoiner测试方法
     */
    @Test
    public void stringJoinerTest() {
        //使用分隔符拼接
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add("哈士奇").add("柯基").add("柴犬");
        System.out.println(stringJoiner);

        //使用前缀、后缀、分隔符进行拼接
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add("哈士奇1号").add("柯基1号").add("柴犬1号");
        System.out.println(sj);

        //设置默认空值
        StringJoiner string = new StringJoiner(",", "[", "]");
        string.setEmptyValue("哈哈");
        System.out.println(string);

        String join = String.join(",", Arrays.asList("1", "2", "3"));
        System.out.println(join);
    }

    /**
     * 使用try catch后出现异常会打印异常信息，然后继续执行
     */
    @Test
    public void exceptionTest() {
        try {
            System.out.println(10 / 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("哈哈哈");
    }

    /**
     * 获取泛型的类型，对于Object、接口和原始类型返回null，对于数 组class则是返回Object.class
     */
    @Test
    public void typeTest(){
        List<String> stringList = new ArrayList<>();
        stringList.add("1");

        Type genericSuperclass = stringList.getClass().getGenericSuperclass();
        Type type = ((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
        //E
        System.out.println(type.getTypeName());
    }

}
