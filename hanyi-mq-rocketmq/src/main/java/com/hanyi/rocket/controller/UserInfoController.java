package com.hanyi.rocket.controller;

import com.hanyi.rocket.pojo.UserInfo;
import com.hanyi.rocket.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户控制层
 * </p>
 *
 * @author wenchangwei
 * @since 9:58 下午 2020/12/9
 */
@RestController
public class UserInfoController {

    /**
     * 用户服务层
     */
    @Resource
    private UserInfoService userInfoService;

    /**
     * 新增用户信息
     *
     * @param userInfo 用户对象
     */
    @PostMapping("/saveBefore")
    public void saveUserInfoBefore(@RequestBody UserInfo userInfo) {
        userInfoService.saveUserInfoBefore(userInfo);
    }

}
