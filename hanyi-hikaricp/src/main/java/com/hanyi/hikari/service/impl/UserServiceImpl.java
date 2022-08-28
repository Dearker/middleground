package com.hanyi.hikari.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.hikari.common.component.UserComponent;
import com.hanyi.hikari.dao.UserDao;
import com.hanyi.hikari.dao.UserInfoDao;
import com.hanyi.hikari.pojo.UserEntity;
import com.hanyi.hikari.pojo.UserInfoEntity;
import com.hanyi.hikari.request.UserQueryPageParam;
import com.hanyi.hikari.service.UserService;
import com.hanyi.hikari.vo.UserTotalVo;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * <p>
 * 用户逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 9:39 上午 2020/12/12
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private UserComponent userComponent;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 线程池
     */
    private final ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(4, 4);

    private final List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<>());

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

    /**
     * 删除用户的用户名
     *
     * @param userName 用户名
     */
    @Override
    public void removeUserByUserName(String userName) {
        this.lambdaUpdate().like(UserEntity::getUserName, userName).set(UserEntity::getDeleted, 1).update();
    }

    /**
     * 获取用户的用户名
     *
     * @param userName 用户名
     * @return 返回用户集合
     */
    @Override
    public List<UserEntity> getUserByUserName(String userName) {
        IPage<UserEntity> page = new Page<>(0, 3);
        return this.lambdaQuery().like(UserEntity::getUserName, userName).page(page).getRecords();
    }

    /**
     * 批量保存用户，此处必须调用方和被调用方都使用@Transactional，具体操作事务的代码不能和本类写在同一个文件，
     * 否则动态代理无法获取到对应的数据源
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void batchSaveUserByTransactionStatus(int number) {
        List<UserEntity> userEntityList = this.buildDataList().getKey();
        List<UserInfoEntity> userInfoEntityList = this.buildDataList().getValue();

        CountDownLatch countDownLatch = new CountDownLatch(4);
        AtomicBoolean isError = new AtomicBoolean(false);

        try {
            for (int i = 0; i < 4; i++) {
                List<UserEntity> limitList = userEntityList.stream().skip(i).limit(1).collect(Collectors.toList());
                List<UserInfoEntity> infoEntityList = userInfoEntityList.stream().skip(i).limit(1).collect(Collectors.toList());
                int finalI = i;
                threadPoolExecutor.execute(() -> {
                    try {
                        userComponent.batchAdd(limitList, infoEntityList, transactionStatuses, finalI, number);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        isError.set(true);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }

            // 倒计时锁设置超时时间 30s
            boolean await = countDownLatch.await(10, TimeUnit.SECONDS);
            // 判断是否超时
            if (!await) {
                isError.set(true);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
            isError.set(true);
        }

        if (!transactionStatuses.isEmpty()) {
            if (isError.get()) {
                transactionStatuses.forEach(s -> transactionManager.rollback(s));
            } else {
                transactionStatuses.forEach(s -> transactionManager.commit(s));
            }
        }
    }

    @Override
    public void batchSaveByConnection(int number) {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession(sqlSessionTemplate.getSqlSessionFactory(),
                sqlSessionTemplate.getExecutorType(), sqlSessionTemplate.getPersistenceExceptionTranslator());
        Connection connection = sqlSession.getConnection();

        List<UserEntity> userEntityList = this.buildDataList().getKey();
        List<UserInfoEntity> userInfoEntityList = this.buildDataList().getValue();

        try {
            // 设置手动提交
            connection.setAutoCommit(false);
            //获取mapper
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            UserInfoDao userInfoDao = sqlSession.getMapper(UserInfoDao.class);
            List<Callable<Integer>> callableList = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                List<UserEntity> limitList = userEntityList.stream().skip(i).limit(1).collect(Collectors.toList());
                List<UserInfoEntity> infoEntityList = userInfoEntityList.stream().skip(i).limit(1).collect(Collectors.toList());
                int finalI = i;

                //使用返回结果的callable去执行,
                Callable<Integer> callable = () -> {
                    //让最后一个线程抛出异常
                    if (Objects.equals(finalI, number)) {
                        throw new RuntimeException("出现事务异常");
                    }
                    userInfoDao.batchSaveUserInfo(infoEntityList);
                    return userDao.batchSaveUser(limitList);
                };
                callableList.add(callable);
            }

            //执行子线程
            List<Future<Integer>> futures = threadPoolExecutor.invokeAll(callableList);
            for (Future<Integer> future : futures) {
                //如果有一个执行不成功,则全部回滚
                if (future.get() <= 0) {
                    connection.rollback();
                    return;
                }
            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("error", e);
            }

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("数据库连接关闭失败：{}", e.getMessage());
            }
        }
    }

    /**
     * 构建数据列表
     *
     * @return {@link Pair}<{@link List}<{@link UserEntity}>, {@link List}<{@link UserInfoEntity}>>
     */
    private Pair<List<UserEntity>, List<UserInfoEntity>> buildDataList() {
        List<UserEntity> userEntityList = new ArrayList<>();
        List<UserInfoEntity> userInfoEntityList = new ArrayList<>();
        LongStream.range(10, 14).forEach(s -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(s);
            userEntity.setUserName("userName: " + s);
            userEntityList.add(userEntity);
            userInfoEntityList.add(new UserInfoEntity(s, "idCard: " + s, "info: " + s));
        });
        return new Pair<>(userEntityList, userInfoEntityList);
    }

}
