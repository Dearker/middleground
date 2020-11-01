package com.hanyi.daily.common.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <p>
 * 将接收的前端字符串类型转换成Long类型
 * </p>
 *
 * @author wenchangwei
 * @since 11:06 下午 2020/10/12
 */
@Slf4j
public class LongJsonDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        try {
            return StrUtil.isBlank(value) ? null : Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.error("数据转换异常", e);
            return null;
        }
    }
}
