package com.hanyi.daily.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * 向前端返回时将Long转成字符串
 * </p>
 *
 * @author wenchangwei
 * @since 11:04 下午 2020/10/12
 */
public class LongJsonSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (Objects.nonNull(value)) {
            jsonGenerator.writeString(value.toString());
        }
    }
}