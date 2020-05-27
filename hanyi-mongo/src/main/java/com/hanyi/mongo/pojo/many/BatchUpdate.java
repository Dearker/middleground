package com.hanyi.mongo.pojo.many;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * <p>
 * MongoDB 批量更新组合类
 * </p>
 *
 * @author wenchangwei
 * @since 9:21 下午 2020/5/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchUpdate {

    /**
     * 查询对象
     */
    private Query query;

    /**
     * 更新对象
     */
    private Update update;

}
