package com.hanyi.hikari.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hanyi.hikari.ApplicationTest;
import com.hanyi.hikari.dao.UserDao;
import com.hanyi.hikari.pojo.UserEntity;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/8/27 9:15 PM
 */
public class UserServiceTest extends ApplicationTest {

    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    @Test
    public void saveTest() {
        userService.batchSaveByConnection(5);
    }

    @Test
    public void errorSaveTest() {
        userService.batchSaveByConnection(3);
    }

    @Test
    public void batchSaveUserTest() {
        userService.batchSaveUserByTransactionStatus(5);
    }

    @Test
    public void errorBatchSaveTest() {
        userService.batchSaveUserByTransactionStatus(3);
    }

    @Test
    public void deleteUserTest() {
        userDao.delete(new LambdaQueryWrapper<UserEntity>().gt(UserEntity::getId, 10));
    }

}
