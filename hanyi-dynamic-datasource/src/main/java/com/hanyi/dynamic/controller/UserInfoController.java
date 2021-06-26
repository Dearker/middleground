package com.hanyi.dynamic.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hanyi.dynamic.common.component.DynamicChangeComponent;
import com.hanyi.dynamic.pojo.DataBaseConfigInfo;
import com.hanyi.dynamic.pojo.UserInfo;
import com.hanyi.dynamic.service.DataBaseConfigService;
import com.hanyi.dynamic.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户详情数据层
 * </p>
 *
 * @author wenchangwei
 * @since 2021/6/26 11:51 上午
 */
@RestController
@RequestMapping("/userInfo")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    private final DataBaseConfigService dataBaseConfigService;

    private final DynamicChangeComponent dynamicChangeComponent;

    @PostMapping("/insert")
    public void insert(@RequestBody UserInfo userInfo){
        dynamicChangeComponent.changeDataSource(1L);
        userInfo.setId(RandomUtil.randomLong(Integer.MAX_VALUE));
        userInfo.setCreateTime(LocalDateTime.now());
        userInfoService.save(userInfo);

        dynamicChangeComponent.changeDataSource(3L);
        userInfo.setId(RandomUtil.randomLong(Integer.MAX_VALUE));
        userInfo.setUserName(userInfo.getUserName() + "哈哈哈");
        userInfo.setCreateTime(LocalDateTime.now());
        userInfoService.save(userInfo);

        DynamicDataSourceContextHolder.clear();
    }

    @GetMapping("/select")
    public List<UserInfo> findAllUserInfoList(){
        dynamicChangeComponent.changeDataSource(1L);
        List<UserInfo> userInfoList = userInfoService.list();

        dynamicChangeComponent.changeDataSource(2L);
        userInfoList.addAll(userInfoService.list());
        DynamicDataSourceContextHolder.clear();
        return userInfoList;
    }

    @GetMapping("/findConfig")
    public List<DataBaseConfigInfo> findConfig(){
        dynamicChangeComponent.changeDataSource(3L);
        List<DataBaseConfigInfo> configInfoList = dataBaseConfigService.list();
        DynamicDataSourceContextHolder.clear();
        return configInfoList;
    }

}
