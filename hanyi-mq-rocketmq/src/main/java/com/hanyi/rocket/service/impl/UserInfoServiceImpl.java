package com.hanyi.rocket.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.rocket.common.constant.MessageConstant;
import com.hanyi.rocket.dao.TransactionLogDao;
import com.hanyi.rocket.dao.UserInfoDao;
import com.hanyi.rocket.pojo.TransactionLog;
import com.hanyi.rocket.pojo.UserInfo;
import com.hanyi.rocket.service.UserInfoService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 9:56 下午 2020/12/9
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

    /**
     * 事务日志数据层
     */
    @Resource
    private TransactionLogDao transactionLogDao;

    /**
     * mq模板
     */
    @Resource
    private RocketMQTemplate rocketTemplate;

    /**
     * 分布式id对象
     */
    @Resource
    private Snowflake snowflake;

    /**
     * 发送保存用户消息
     *
     * @param userInfo 用户对象
     */
    @Override
    public void saveUserInfoBefore(UserInfo userInfo) {
        rocketTemplate.sendMessageInTransaction(MessageConstant.TX_PRODUCER_GROUP, MessageConstant.TX_TOPIC,
                MessageBuilder.withPayload(userInfo).setHeader(MessageConstant.TX_ID, snowflake.nextId()).build(), userInfo);
    }

    /**
     * 保存用户信息
     *
     * @param userInfo       用户对象
     * @param transactionLog 事务日志
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserInfo(UserInfo userInfo, TransactionLog transactionLog) {
        baseMapper.insert(userInfo);
        transactionLogDao.insert(transactionLog);
    }
}
