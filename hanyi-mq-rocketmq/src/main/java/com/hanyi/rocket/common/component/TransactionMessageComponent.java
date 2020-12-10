package com.hanyi.rocket.common.component;

import com.alibaba.fastjson.JSON;
import com.hanyi.rocket.common.constant.MessageConstant;
import com.hanyi.rocket.dao.TransactionLogDao;
import com.hanyi.rocket.pojo.TransactionLog;
import com.hanyi.rocket.pojo.UserInfo;
import com.hanyi.rocket.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 事务消息处理类
 * </p>
 *
 * @author wenchangwei
 * @since 9:03 下午 2020/12/9
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = MessageConstant.TX_PRODUCER_GROUP)
public class TransactionMessageComponent implements RocketMQLocalTransactionListener {

    /**
     * 用户数据层
     */
    @Resource
    private UserInfoService userInfoService;

    /**
     * 事务消息数据层
     */
    @Resource
    private TransactionLogDao transactionLogDao;

    /**
     * 执行本地事物
     *
     * @param message 消息对象
     * @param arg     发送消息的参数对象
     * @return 返回事务状态
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        try {
            Object txId = message.getHeaders().get(MessageConstant.TX_ID);
            if (Objects.nonNull(txId)) {
                long txIdLong = Long.parseLong(txId.toString());
                log.info("获取的消息id: {}", txIdLong);

                if (arg instanceof UserInfo) {
                    UserInfo userInfo = (UserInfo) arg;
                    String jsonString = JSON.toJSONString(userInfo);
                    userInfoService.saveUserInfo(userInfo, new TransactionLog(txIdLong, jsonString, LocalDateTime.now()));
                }
            }

            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error(e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 消息会查
     *
     * @param message 消息对象
     * @return 返回会查状态
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {

        Object txId = message.getHeaders().get(MessageConstant.TX_ID);
        if (Objects.nonNull(txId)) {
            long txIdLong = Long.parseLong(txId.toString());
            log.info("获取的消息id: {}", txIdLong);

            //查询日志记录
            TransactionLog transactionLog = transactionLogDao.selectById(txIdLong);
            return Objects.isNull(transactionLog) ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
        }

        return RocketMQLocalTransactionState.COMMIT;
    }
}
