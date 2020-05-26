package com.hanyi.mongo.pojo.many;

import com.hanyi.mongo.pojo.many.incloud.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>
 * mongo 巡检任务关联模板实体类
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 11:52 2020/5/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inspection_task_template")
public class InspectionTaskTemplate {

    /**
     * 编号
     */
    @Id
    private String taskTemplateId;

    /**
     * 巡检任务id
     */
    private String inspectionTaskId;

    /**
     * 巡检模板对象
     */
    private Template template;

    /**
     * 多租户id
     */
    private String tenantId;
}
