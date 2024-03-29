package com.hanyi.daily.load;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.TableMap;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hanyi.daily.common.enums.CodeEnum;
import com.hanyi.daily.common.enums.IntEnum;
import com.hanyi.daily.common.event.ExtApplicationEvent;
import com.hanyi.daily.pojo.PersonInfo;
import com.hanyi.daily.pojo.User;
import com.hanyi.daily.property.Book;
import com.hanyi.daily.property.PersonDesc;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
        System.out.println("默认字符编码：" + Charset.defaultCharset());
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
        PersonDesc personDesc = this.getObject(PersonDesc.class);
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

        System.out.println("内网ip：" + NetUtil.getLocalhostStr());
        System.out.println(NetUtil.localIpv4s());

        System.out.println("外网ip：" + getInternetIp());
    }

    /**
     * 获取外网ip
     *
     * @return 返回外网ip地址
     */
    private static String getInternetIp() {
        String localhostStr = NetUtil.getLocalhostStr();
        InetAddress ip;
        Enumeration<InetAddress> address;
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                address = networks.nextElement().getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (ip instanceof Inet4Address && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(localhostStr)) {
                        return ip.getHostAddress();
                    }
                }
            }

            // 如果没有外网IP，就返回内网IP
            return localhostStr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            throw new IOException();
        } catch (IOException | ArithmeticException e) {
            e.printStackTrace();
        }
        System.out.println("哈哈哈");
    }

    /**
     * 获取泛型的类型，对于Object、接口和原始类型返回null，对于数 组class则是返回Object.class
     */
    @Test
    public void typeTest() {
        Stream.of(ReflectUtil.getFields(PersonDesc.class)).forEach(s -> {
            Type list = TypeUtil.getFieldType(PersonDesc.class, s.getName());
            System.out.println(list.getTypeName() + " || " + s.getName());
        });

        //获取具体化参数的类型
        Type typeArgument = TypeUtil.getTypeArgument(ExtApplicationEvent.class);
        System.out.println(typeArgument);
    }

    /**
     * 身份证测试方法
     */
    @Test
    public void idCardTest() {
        String idCard = "321083197812162119";

        //获取生日：19781216
        String birthByIdCard = IdcardUtil.getBirthByIdCard(idCard);
        System.out.println(birthByIdCard);

        //格式化日期：1978-12-16
        String format = DateUtil.format(IdcardUtil.getBirthDate(idCard), DatePattern.NORM_DATE_PATTERN);
        System.out.println(format);

        //年龄: 42
        System.out.println(IdcardUtil.getAgeByIdCard(idCard));

        //省份: 江苏
        System.out.println(IdcardUtil.getProvinceByIdCard(idCard));
    }

    /**
     * 树结构测试
     */
    @Test
    public void treeTest() {
        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();

        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "11", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));

        // 0表示最顶层的id是0
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        System.out.println(treeList);
    }

    /**
     * 缓存测试,FIFO指定容量，如果添加的元素超过了指定的容量则将最先添加的元素移除
     */
    @Test
    public void cacheTest() {
        TimeInterval timer = DateUtil.timer();
        FIFOCache<String, Integer> fifoCache = CacheUtil.newFIFOCache(Integer.BYTES, 5000);
        fifoCache.put("aaa", 1);
        fifoCache.put("bbb", 2);
        fifoCache.put("ccc", 3);
        fifoCache.put("ddd", 4);
        fifoCache.put("eee", 5);

        fifoCache.cacheObjIterator().forEachRemaining(s -> System.out.println(s.getKey() + "||" + s.getValue()));

        ThreadUtil.sleep(5000);
        fifoCache.cacheObjIterator().forEachRemaining(s -> System.out.println(s.getKey() + "||" + s.getValue()));
        System.out.println("执行耗时：" + timer.intervalMs());
    }

    /**
     * 可重复键值
     */
    @Test
    public void tableMapTest() {
        TableMap<String, Integer> tableMap = new TableMap<>(5);
        tableMap.put("aaa", 111);
        tableMap.put("aaa", 222);
        tableMap.put("bbb", 111);
        tableMap.put("ccc", 444);

        System.out.println(tableMap);
        //[aaa, bbb]
        System.out.println(tableMap.getKeys(111));
        //[111, 222]
        System.out.println(tableMap.getValues("aaa"));
    }

    /**
     * optional 测试方法
     */
    @Test
    public void optionalTest() {
        PersonDesc personDesc = new PersonDesc();
        String orElse = Optional.of(personDesc).map(PersonDesc::getName).orElse(StrUtil.EMPTY_JSON);
        System.out.println("空：" + orElse);

        personDesc.setName("哈士奇");
        String s = Optional.of(personDesc).map(PersonDesc::getName).orElse(StrUtil.EMPTY_JSON);
        System.out.println("name: " + s);

        PersonDesc p = null;
        String anElse = Optional.ofNullable(p).map(PersonDesc::getName).orElse(null);
        System.out.println(anElse);
    }

    @Test
    public void optionsMapTest() {
        PersonDesc personDesc = new PersonDesc();
        personDesc.setName("哈哈哈");
        String orElse = Optional.of(personDesc).map(s -> {
            User user = new User();
            user.setUserName(s.getName());
            return user;
        }).map(User::getUserName).orElse(null);
        System.out.println(orElse);

        personDesc.setName(null);
        String name = Optional.of(personDesc).map(s -> {
            User user = new User();
            user.setUserName(s.getName());
            return user;
        }).map(User::getUserName).orElse(null);
        System.out.println(name);

        List<String> stringList = null;
        List<String> list = Optional.of(stringList).orElse(Collections.singletonList("1"));
        System.out.println(list);

        stringList = new ArrayList<>();
        list = Optional.of(stringList).orElse(Collections.singletonList("1"));
        System.out.println(list);
    }

    /**
     * 慎用继承，后续排除问题可能会提高难度
     * 子类的equals()和hashCode()、toString()方法都会继承父类的方法，即直接使用父类的方法，
     * 所以直接打印子类的对象时，没有输出父类的属性值，但属性其实已经完成了复制
     * <p>
     * 使用@SuperBuilder需要在子类和父类中同时使用，并且空参构造和全参构造都需要实现
     */
    @Test
    public void personTest() {
        com.hanyi.daily.pojo.Person person = new com.hanyi.daily.pojo.Person(1, "柯基");

        PersonInfo personInfo = new PersonInfo();
        BeanUtil.copyProperties(person, personInfo);

        BeanUtil.beanToMap(personInfo).forEach((k, v) -> System.out.println(k + " || " + v));
        System.out.println("直接输出的对象：" + personInfo);

        PersonInfo build = PersonInfo.builder()
                .id(1)
                .age(2)
                .build();
        System.out.println(build);
    }

    /**
     * Objects.toString()和String.valueOf()效果一样，如果传入的对象为null，则输出的为"null"字符串
     */
    @Test
    public void stringTest() {
        Object object = null;
        String valueOf = String.valueOf(object);
        System.out.println(valueOf);
        String toString = Objects.toString(object);
        System.out.println(toString);

        String string = Objects.toString(object, "1");
        System.out.println(string);
    }

    /**
     * 字符串格式化，可用于拼接或转换成字符串内容
     * %s	字符串类型
     * %c	字符类型
     * %b	布尔类型
     * %d	整数类型 （十进制）
     * %n	换行符
     */
    @Test
    public void stringFormatTest() {
        String strFormat = String.format(" %s/%s/%s ", "柯基", "哈士奇", "柴犬");
        System.out.println(strFormat);

        String charFormat = String.format(" %c/%c/%c ", '柯', '哈', '柴');
        System.out.println(charFormat);

        String booleanFormat = String.format(" %b/%b/%b ", false, true, false);
        System.out.println(booleanFormat);

        String numberFormat = String.format(" %d?%d?%d", 12, 13, 14);
        System.out.println(numberFormat);
    }

    /**
     * 字符串格式化时间
     * T	“HH:MM:SS”格式（24时制）
     * R	“HH:MM”格式（24时制）
     * c	包括全部日期和时间信息	   星期六 十月 27 14:21:20 CST 2007
     * F	“年-月-日”格式	       2007-10-27
     * D	“月/日/年”格式	       10/27/07
     * r	“HH:MM:SS PM”格式（12时制）	02:25:51 下午
     */
    @Test
    public void stringFormatDateTest() {
        LocalTime localTime = LocalTime.now();
        //21:38:46
        String allFormat = String.format(" %tT ...", localTime);
        System.out.println(allFormat);

        String format = String.format(" %tR ", localTime);
        System.out.println(format);
    }

    @Test
    public void mapStringFormatTest(){
        Map<String,Object> paramMap = new HashMap<>(2);
        paramMap.put("name","柯基");
        paramMap.put("age",26);

        System.out.println(StrUtil.format("the string name is {name}, the age is {age}", paramMap));
    }

    /**
     * insert(): 在索引的前面添加字符串
     */
    @Test
    public void stringInsertTest() {
        StringBuilder stringBuilder = new StringBuilder("BCD");
        stringBuilder.insert(0, "A");
        System.out.println(stringBuilder);
    }

    /**
     * setLength(): 用于清空stringBuilder中的内容
     */
    @Test
    public void stringBuilderTest() {
        String source = "a:1,b:2,c:3,d:4";
        int index = source.indexOf(':');
        String target = source.replace(':', '=');
        System.out.println(index + "||" + target);

        StringBuilder sb = new StringBuilder(20);
        sb.append("1:").append("2:").append("3:").append("4");
        System.out.println(sb.toString());
        sb.setLength(0);
        sb.append("5:").append("6:").append("7:").append("8");
        System.out.println(sb.toString());
    }

    /**
     * 枚举的ordinal方法一般不使用，枚举的排序使用的是索引位置排序
     */
    @Test
    public void enumTest() {
        int zero = IntEnum.ZERO.ordinal();
        String zeroName = IntEnum.ZERO.name();
        System.out.println(zero + "||" + zeroName);

        int one = IntEnum.ONE.ordinal();
        String oneName = IntEnum.ONE.name();
        System.out.println(one + "||" + oneName);

        int two = IntEnum.TWO.ordinal();
        String twoName = IntEnum.TWO.name();
        System.out.println(two + "||" + twoName);

        int three = IntEnum.THREE.ordinal();
        String threeName = IntEnum.THREE.name();
        System.out.println(three + "||" + threeName);

        List<IntEnum> intEnumList = new ArrayList<>();
        intEnumList.add(IntEnum.THREE);
        intEnumList.add(IntEnum.ONE);
        intEnumList.add(IntEnum.ZERO);
        intEnumList.add(IntEnum.TWO);

        System.out.println(intEnumList);
        intEnumList.sort(Comparator.naturalOrder());
        System.out.println(intEnumList);
    }

    @Test
    public void codeEnumTest() {
        CodeEnum codeEnum = CodeEnum.valueOf(CodeEnum.ONE.name());
        System.out.println(codeEnum.getCode() + "||" + codeEnum.getCodeName());
        System.out.println(CodeEnum.ONE.getCode() + "||" + CodeEnum.ONE.getCodeName());
    }

    /**
     * 获取当前系统语言环境 消除判断
     */
    @Test
    public void languageTest() {
        Locale locale = Locale.getDefault();
        System.out.println(locale.getLanguage());
        System.out.println(locale.getCountry());
    }

    /**
     * set集合中添加元素，如果已经存在元素则返回false，表示添加失败，remove方法类似，如果删除的元素不存在则返回false
     */
    @Test
    public void addSetTest() {
        Set<Integer> integerSet = new HashSet<>();

        System.out.println(integerSet.add(1));
        System.out.println(integerSet.add(1));

        System.out.println(integerSet.remove(1));
        System.out.println(integerSet.remove(2));
    }

    /**
     * 加解密
     */
    @Test
    public void secureTest(){
        AES aes = SecureUtil.aes();
        //加密
        String encryptHex = aes.encryptHex("123");
        System.out.println(encryptHex);

        //解密
        String decryptStr = aes.decryptStr(encryptHex);
        System.out.println(decryptStr);
    }

}
