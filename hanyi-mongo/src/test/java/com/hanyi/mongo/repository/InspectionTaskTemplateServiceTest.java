package com.hanyi.mongo.repository;

import com.hanyi.mongo.MongodbApplicationTests;
import com.hanyi.mongo.pojo.many.InspectionTaskTemplate;
import com.hanyi.mongo.pojo.many.incloud.InspectionItem;
import com.hanyi.mongo.pojo.many.incloud.Template;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 7:37 下午 2020/5/26
 */
public class InspectionTaskTemplateServiceTest extends MongodbApplicationTests {

    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    public void insert() {

        List<InspectionItem> inspectionItemList = new ArrayList<>(1);
        InspectionItem inspectionItem = InspectionItem.builder()
                .templateItemId("1212")
                .templateItemName("模板项目名称")
                .remark("备注")
                .sort(1)
                .build();
        inspectionItemList.add(inspectionItem);
        Template template = Template.builder()
                .templateName("模板名称")
                .templateId("1")
                .inspectionItemList(inspectionItemList)
                .build();

        InspectionTaskTemplate inspectionTaskTemplate = InspectionTaskTemplate.builder()
                .inspectionTaskId("1")
                .taskTemplateId("2")
                .tenantId("3")
                .template(template)
                .build();

        mongoTemplate.insert(inspectionTaskTemplate, "inspection_task_template");
    }

    @Test
    public void queryByIdTest() {

        Query query = new Query(Criteria.where("inspectionTaskId").is("1"));
        InspectionTaskTemplate inspectionTaskTemplate = mongoTemplate.findOne(query, InspectionTaskTemplate.class);

        System.out.println("获取的数据：" + inspectionTaskTemplate);

    }

}
