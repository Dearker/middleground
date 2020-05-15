package com.hanyi.mongo.repository;

import cn.hutool.core.lang.Snowflake;
import com.hanyi.mongo.MongodbApplicationTests;
import com.hanyi.mongo.pojo.ProcDescribe;
import com.hanyi.mongo.pojo.ProcInfo;
import com.hanyi.mongo.pojo.UnwindProcInfo;
import com.hanyi.mongo.service.ProcInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.repository
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-14 21:56
 * @Version: 1.0
 */
@Slf4j
public class ProcInfoServiceTest extends MongodbApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private ProcInfoService procInfoService;

    @Test
    public void insertTest() {

        List<ProcInfo> procInfoList = new ArrayList<>(2);

        List<ProcDescribe> procDescribeList = new ArrayList<>(2);
        procDescribeList.add(ProcDescribe.builder().type(0).name("哈哈哈").build());
        procDescribeList.add(ProcDescribe.builder().type(1).name("呵呵呵").build());
        ProcInfo procInfo = ProcInfo.builder().id(snowflake.nextId())
                .typeInfo("哈士奇").procDescribes(procDescribeList).build();
        procInfoList.add(procInfo);

        List<ProcDescribe> procDescribeList2 = new ArrayList<>(2);
        procDescribeList2.add(ProcDescribe.builder().type(0).name("护手霜").build());
        procDescribeList2.add(ProcDescribe.builder().type(1).name("康卡斯").build());
        ProcInfo procInfo2 = ProcInfo.builder().id(snowflake.nextId())
                .typeInfo("柯基").procDescribes(procDescribeList2).build();
        procInfoList.add(procInfo2);

        mongoTemplate.insertAll(procInfoList);
        log.info("插入完成");
    }

    @Test
    public void unwindTest(){
        List<UnwindProcInfo> proInfoByCondition = procInfoService.findProInfoByCondition();
        proInfoByCondition.forEach(s-> System.out.println("获取的数据："+ s));

    }


}
