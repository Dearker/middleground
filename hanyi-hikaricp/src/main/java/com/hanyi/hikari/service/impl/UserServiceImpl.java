package com.hanyi.hikari.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.hikari.dao.UserDao;
import com.hanyi.hikari.pojo.UserEntity;
import com.hanyi.hikari.request.UserQueryPageParam;
import com.hanyi.hikari.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 用户逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 9:39 上午 2020/12/12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    /**
     * 根据条件查询用户信息
     *
     * @param userQueryPageParam 查询对象
     * @return 返回分页结果
     */
    @Override
    public Page<UserEntity> findUserByCondition(UserQueryPageParam userQueryPageParam) {

        Long currentPage = userQueryPageParam.getCurrentPage();
        Long pageSize = userQueryPageParam.getPageSize();

        //设置起始索引
        userQueryPageParam.setStartIndex((currentPage - 1) * pageSize);

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();

        //设置开始时间
        String beginAge = userQueryPageParam.getBeginAge();
        if (StrUtil.isNotBlank(beginAge)) {
            int beginYear = year - Integer.parseInt(beginAge);
            String beginData = beginYear + StrUtil.DASHED + month;
            userQueryPageParam.setBeginAge(beginData);
        }

        //设置结束时间
        String endAge = userQueryPageParam.getEndAge();
        if (StrUtil.isNotBlank(endAge)) {
            int endYear = year - Integer.parseInt(endAge);
            String endData = endYear + StrUtil.DASHED + month;
            userQueryPageParam.setEndAge(endData);
        }

        Long total = baseMapper.countUserByCondition(userQueryPageParam);
        if (total == 0) {
            return new Page<>(currentPage, pageSize);
        }

        List<UserEntity> userByCondition = baseMapper.findUserByCondition(userQueryPageParam);
        Page<UserEntity> page = new Page<>(currentPage, pageSize, total);
        page.setRecords(userByCondition);

        return page;
    }
}
