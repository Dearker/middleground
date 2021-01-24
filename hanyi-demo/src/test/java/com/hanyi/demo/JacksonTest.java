package com.hanyi.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hanyi.demo.entity.Address;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Jackson测试类
 * </p>
 *
 * @author wenchangwei
 * @since 4:17 下午 2021/1/23
 */
public class JacksonTest {

    /**
     * 将实体类转换成json字符串
     *
     * @throws JsonProcessingException 异常
     */
    @Test
    public void writerTest() throws JsonProcessingException {
        Address address = new Address("哈哈哈", "杭州", "123");

        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(address);
        System.out.println(valueAsString);
    }

    /**
     * 将json字符串转换成实体类
     *
     * @throws IOException 异常
     */
    @Test
    public void readTest() throws IOException {
        Address address = new Address("哈哈哈", "杭州", "123");

        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(address);

        Address readValue = objectMapper.readValue(valueAsString, Address.class);
        System.out.println(readValue);
    }

    /**
     * 将实体类型转换成指定的类型
     *
     * @throws IOException 异常
     */
    @Test
    public void typeTest() throws IOException {
        Map<String, Address> addressMap = new HashMap<>();
        addressMap.put("1", new Address("哈哈哈", "杭州", "123"));
        addressMap.put("2", new Address("看看看", "北京", "12345"));

        ObjectMapper objectMapper = new ObjectMapper();
        String writeValueAsString = objectMapper.writeValueAsString(addressMap);

        Map<String, Address> readValue = objectMapper.readValue(writeValueAsString, new TypeReference<Map<String, Address>>() {
        });

        System.out.println(readValue);
    }

    /**
     * 指定序列化规则
     *
     * @throws IOException 异常
     */
    @Test
    public void featureTest() throws IOException {
        Address address = Address.builder().city("1").street("2").build();

        ObjectMapper objectMapper = new ObjectMapper();
        String writeValueAsString = objectMapper.writeValueAsString(address);

        objectMapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        Address readValue = objectMapper.readValue(writeValueAsString, Address.class);

        System.out.println(readValue);
    }

    /**
     * 将对象转换成json的map结构
     *
     * @throws IOException 异常
     */
    @Test
    public void nodeTest() throws IOException {
        Address address = new Address("哈哈哈", "杭州", "123");

        ObjectMapper objectMapper = new ObjectMapper();
        String writeValueAsString = objectMapper.writeValueAsString(address);

        JsonNode jsonNode = objectMapper.readTree(writeValueAsString);
        jsonNode.fieldNames().forEachRemaining(s -> System.out.println(jsonNode.get(s)));
    }

}
