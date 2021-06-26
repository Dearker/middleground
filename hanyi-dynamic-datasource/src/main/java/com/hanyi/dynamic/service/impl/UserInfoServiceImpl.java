package com.hanyi.dynamic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.dynamic.dao.UserInfoMapper;
import com.hanyi.dynamic.pojo.UserInfo;
import com.hanyi.dynamic.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * 用户详情逻辑层
 *
 * @author weiwen
 * @date 2021/06/26
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}




