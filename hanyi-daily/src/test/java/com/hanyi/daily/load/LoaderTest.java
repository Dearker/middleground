package com.hanyi.daily.load;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hanyi.daily.pojo.User;
import com.hanyi.daily.property.Book;
import com.hanyi.daily.property.Person;
import org.junit.Test;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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
     * 根据字节码获取其对应的对象
     */
    @Test
    public void genericTypeTest() {
        Person person = getObject(Person.class);
        Book book = getObject(Book.class);
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
        List common = this.buildParams(person);

        System.out.println(common);
    }

    private List<Serializable> buildParams(Serializable serializable) {
        List<Serializable> list = new ArrayList<>();
        list.add(serializable);
        return list;
    }


}
