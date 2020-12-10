package com.hanyi.rocket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.rocket.pojo.TransactionLog;
import com.hanyi.rocket.pojo.UserInfo;

/**
 * <p>
 * 用户接口层
 * </p>
 *
 * @author wenchangwei
 * @since 9:54 下午 2020/12/9
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 发送保存用户消息
     *
     * @param userInfo 用户对象
     */
    void saveUserInfoBefore(UserInfo userInfo);

    /**
     * 保存用户信息
     *
     * @param userInfo       用户对象
     * @param transactionLog 事务日志
     */
    void saveUserInfo(UserInfo userInfo, TransactionLog transactionLog);

}
