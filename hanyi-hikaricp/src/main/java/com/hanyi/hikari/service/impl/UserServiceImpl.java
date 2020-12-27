package com.hanyi.hikari.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.hikari.dao.UserDao;
import com.hanyi.hikari.pojo.UserEntity;
import com.hanyi.hikari.request.UserQueryPageParam;
import com.hanyi.hikari.service.UserService;
import com.hanyi.hikari.vo.UserTotalVo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询用户信息
     *
     * @param userQueryPageParam 查询对象
     * @return 返回分页结果
     */
    @Override
    public Page<UserEntity> findUserByPage(UserQueryPageParam userQueryPageParam) {
        // 参数一：当前页; 参数二：页面大小
        Page<UserEntity> userPage = new Page<>(userQueryPageParam.getCurrentPage(), userQueryPageParam.getPageSize());

        IPage<UserEntity> entityPage = baseMapper.selectPage(userPage, null);
        userPage.setTotal(entityPage.getTotal());
        userPage.setRecords(entityPage.getRecords());

        return userPage;
    }

    /**
     * 根据删除状态进行分组
     *
     * @return 返回分组的结果
     */
    @Override
    public List<UserTotalVo> findUserByGroup() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("version,count(*) as total").groupBy("version");

        List<Map<String, Object>> mapList = baseMapper.selectMaps(queryWrapper);

        List<UserTotalVo> userTotalVoList = new ArrayList<>(mapList.size());
        mapList.forEach(s -> userTotalVoList.add(BeanUtil.toBean(s, UserTotalVo.class)));

        return userTotalVoList;
    }

    /**
     * 根据版本号查询用户集合,使用exists关键字时必须和外表进行关联，自连接或者外连接，
     * 不然会出现要么全部查询出来，要么全部查不出来
     *
     * @param version 版本号
     * @return 返回用户集合
     */
    @Override
    public List<UserEntity> findUserByExist(Integer version) {
        return baseMapper.findUserByExist(version);
    }

    /**
     * 根据版本号和用户名进行or查询，MySQL通过创建并填充临时表的方式来执行union查询。除非确实要消除重复的行，否则建议使用union all。
     * 原因在于如果没有all这个关键词，MySQL会给临时表加上distinct选项，这会导致对整个临时表的数据做唯一性校验，这样做的消耗相当高。
     *
     * @param version  版本号
     * @param userName 用户名称
     * @return 返回查询结果
     */
    @Override
    public List<UserEntity> findUserByUnion(Integer version, String userName) {
        return baseMapper.findUserByUnion(version, userName);
    }

    /**
     * 根据版本号和用户名进行or查询,包含重复数据
     *
     * @param version  版本号
     * @param userName 用户名称
     * @return 返回查询结果
     */
    @Override
    public List<UserEntity> findUerByUnionAll(Integer version, String userName) {
        return baseMapper.findUerByUnionAll(version, userName);
    }
}
