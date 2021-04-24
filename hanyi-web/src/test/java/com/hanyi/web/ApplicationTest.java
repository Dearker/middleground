package com.hanyi.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.web.bo.Car;
import com.hanyi.web.bo.Student;
import com.hanyi.web.bo.User;
import com.hanyi.web.common.annotation.AutowiredExt;
import com.hanyi.web.service.impl.FunctionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <p>
 * 应用测试类
 * </p>
 *
 * @author wenchangwei
 * @since 8:50 下午 2021/4/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    /**
     * 资源加载对象
     */
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @AutowiredExt
    private FunctionServiceImpl functionServiceImpl;

    @AutowiredExt(required = false)
    private Car car;

    @Value("classpath:/application.yml")
    private Resource defaultYmlResource;

    @Value("classpath*:*.yml")
    private Resource[] ymlResources;

    @Value("${user.dir}")
    private String currentProjectRootPath;

    /**
     * applicationContext和beanFactory都是通过组合模式将defaultListableBeanFactory对象作为具体操作IOC的方式
     * <p>
     * beanFactory的具体实现为: DefaultListableBeanFactory
     * applicationContext的具体实现为: GenericWebApplicationContext
     * defaultListableBeanFactory为具体操作IOC容器的对象
     */
    @Test
    public void contextTest() {
        System.out.println(beanFactory);
        System.out.println(applicationContext);
        //DefaultListableBeanFactory
        System.out.println(applicationContext.getAutowireCapableBeanFactory());
        System.out.println(defaultListableBeanFactory);
    }

    /**
     * resourceLoader资源加载对象和applicationContext是同一个对象，
     * 具体实现都是GenericWebApplicationContext
     */
    @Test
    public void resourceLoaderTest(){
        //具体实现为GenericWebApplicationContext
        System.out.println(resourceLoader);
        //true
        System.out.println(resourceLoader == applicationContext);;
    }

    /**
     * 向IOC容器中注入bean对象
     * <p>
     * BeanFactoryUtils用于从指定的IOC容器中查找对象
     * beanOfType()：从容器中获取对象后，容器中会移除该对象
     * beansOfTypeIncludingAncestors(): 通过递归的方式从父子容器中查找指定的对象
     */
    @Test
    public void registerTest() {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("name", "柯基")
                .addPropertyValue("age", "12")
                .addPropertyValue("birthDay", DateUtil.date())
                .getBeanDefinition();
        //通过命名的方式向IOC容器中注入bean对象
        defaultListableBeanFactory.registerBeanDefinition("user", beanDefinition);

        //通过非命名的方式向IOC容器中注入bean对象, bean的名称如下：com.hanyi.web.bo.User#0
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, defaultListableBeanFactory);

        Map<String, User> beansOfType = defaultListableBeanFactory.getBeansOfType(User.class);
        beansOfType.forEach((k, v) -> System.out.println("bean的名称：" + k + "，bean对象：" + v));
    }

    /**
     * applicationContext的getBean(): 直接从IOC容器获取对象，如果容器中没有，则会报错
     * applicationContext的getBeanProvider()：通过getObject()获取对象也会报错，效果和getBean()方法类似
     * <p>
     * 当相同类型同时出现多个名称时，getBean()和ObjectProvider的getIfAvailable()都会报错
     */
    @Test
    public void typeSafeTest() {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("name", "柯基")
                .addPropertyValue("age", "12")
                .addPropertyValue("birthDay", DateUtil.date())
                .getBeanDefinition();
        defaultListableBeanFactory.registerBeanDefinition("user", beanDefinition);
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, defaultListableBeanFactory);

        try {
            User bean = applicationContext.getBean(User.class);
            System.out.println("获取bean对象：" + bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        userObjectProvider.forEach(s -> System.out.println("forEach 的元素：" + s));

        try {
            User ifAvailable = userObjectProvider.getIfAvailable(() -> null);
            System.out.println("从IOC容器中获取对象，如果不存在则返回为null: " + ifAvailable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将@AutowiredExt注解中的require设置成false则表示不是必须加装的组件，找不到对象时不会报错
     */
    @Test
    public void annotationExtTest() {
        System.out.println(functionServiceImpl);
        System.out.println(car);

        Student student = applicationContext.getBean("student", Student.class);
        System.out.println(student);

        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);
    }

    /**
     * 不同的线程中会创建不同的对象，相同的线程中使用的是同一个对象
     */
    @Test
    public void threadScopeTest() {
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(3, 3);
        IntStream.range(0, 3).forEach(s ->
                threadPoolExecutor.execute(() -> {
                    Student student = applicationContext.getBean("student", Student.class);
                    System.out.printf("[Thread id :%d] user = %s%n", Thread.currentThread().getId(), student);
                }));

        ThreadUtil.sleep(1000);
        IntStream.range(0, 3).forEach(s -> {
            Student student = applicationContext.getBean("student", Student.class);
            System.out.println("当前对象：" + student);
        });
    }

    @Test
    public void annotatedBeanDefinitionReaderTest() {
        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(defaultListableBeanFactory);
        beanDefinitionReader.register(Car.class);

        Car bean = defaultListableBeanFactory.getBean(Car.class);
        System.out.println("获取的bean对象：" + bean);
    }

    /**
     * @value 注解资源注入,获取资源集合只能使用数组，使用集合获取会报错
     */
    @Test
    public void valueAnnotationTest() {
        System.out.println("当前目录地址：" + currentProjectRootPath);
        System.out.println("读取指定资源内容" + this.getContent(defaultYmlResource));

        System.out.println("---------------------");
        //通过匹配器的方式查询所有的文件并输出
        Stream.of(ymlResources).map(this::getContent).forEach(System.out::println);
    }

    /**
     * 将资源对象转换成字符串
     *
     * @param resource 资源对象
     * @return 转换后的字符串
     */
    String getContent(Resource resource) {
        EncodedResource encodedResource = new EncodedResource(resource, CharsetUtil.UTF_8);
        // 字符输入流
        try (Reader reader = encodedResource.getReader()) {
            return IoUtil.read(reader);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return StrUtil.EMPTY;
    }

}
