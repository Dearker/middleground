package com.hanyi.clickhouse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.hanyi.clickhouse.dao.HitsTestMapper;
import com.hanyi.clickhouse.pojo.HitsTest;
import com.hanyi.clickhouse.service.HitsTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/7/19 8:33 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClickHouseApplicationTest {

    @Resource
    private HitsTestService hitsTestService;

    @Resource
    private HitsTestMapper hitsTestMapper;

    /**
     * 插入测试
     */
    @Test
    public void insertTest(){
        int i = 10001;
        HitsTest hitsTest = new HitsTest();
        hitsTest.setWatchId(i);
        hitsTest.setJavaEnable(i + 100);
        hitsTest.setTitle("柯基" + i);
        hitsTest.setGoodEvent(i + 20);
        hitsTest.setEventTime(LocalDateTime.now());

        hitsTestService.save(hitsTest);
    }

    /**
     * 浴插入测试
     */
    @Test
    public void bathInsertTest(){
        final int count = 1000;
        List<HitsTest> hitsTestList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            HitsTest hitsTest = new HitsTest();
            hitsTest.setWatchId(i);
            hitsTest.setJavaEnable(i + 100);
            hitsTest.setTitle("柯基" + i);
            hitsTest.setGoodEvent(i + 20);
            hitsTest.setEventTime(LocalDateTime.now());

            hitsTestList.add(hitsTest);
        }
        TimeInterval timer = DateUtil.timer();
        hitsTestService.saveBatch(hitsTestList);
        System.out.println("耗时：" +  timer.intervalRestart());
    }

    /**
     * 删除测试
     */
    @Test
    public void deleteTest(){
        final int id = 10001;
        hitsTestMapper.deleteByWatchId(id);
        HitsTest hitsTest = hitsTestService.getById(id);
        System.out.println("查询的结果：" + hitsTest);
    }

    /**
     * 更新测试
     */
    @Test
    public void updateTest(){
        final int id = 0;
        hitsTestMapper.updateTitleByWatchId("哈士奇111",id);
        HitsTest hitsTest = hitsTestService.getById(id);
        System.out.println("查询的结果：" + hitsTest);
    }

    /**
     * 通过id测试
     */
    @Test
    public void getByIdTest(){
        final int id = 0;
        HitsTest hitsTest = hitsTestService.getById(id);
        System.out.println("查询的结果：" + hitsTest);
    }

}
