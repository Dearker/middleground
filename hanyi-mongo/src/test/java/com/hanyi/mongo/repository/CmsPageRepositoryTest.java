package com.hanyi.mongo.repository;

import com.hanyi.mongo.MongodbApplicationTests;
import com.hanyi.mongo.pojo.CmsPage;
import com.hanyi.mongo.service.PageService;
import com.hanyi.mongo.vo.CmsPageStatistics;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.repository
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-09 21:00
 * @Version: 1.0
 */
@Slf4j
public class CmsPageRepositoryTest extends MongodbApplicationTests {

    @Autowired
    private PageService pageService;


    @Test
    public void findOneTest(){
        CmsPage cmsPage = pageService.getById("5a754adf6abb500ad05688d9");
        System.out.println("获取的数据-->"+ cmsPage);
    }

    @Test
    public void findCountTest(){
        List<CmsPageStatistics> listByGroup = pageService.findListByGroup();
        listByGroup.forEach(s->{
            System.out.println("获取的数据: "+ s.getType()+"||" + s.getCount());
            System.out.println("---------------");
        });
    }


}
