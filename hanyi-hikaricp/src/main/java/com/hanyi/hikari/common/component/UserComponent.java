package com.hanyi.hikari.common.component;

import com.hanyi.hikari.dao.UserDao;
import com.hanyi.hikari.dao.UserInfoDao;
import com.hanyi.hikari.pojo.UserEntity;
import com.hanyi.hikari.pojo.UserInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 多线程事务
 *     参考文档地址
 *        <a href="https://www.cnblogs.com/wk-missQ1/p/16372573.html">https://www.cnblogs.com/wk-missQ1/p/16372573.html</a>
 *     查看代码地址
 *        <a href="https://gitee.com/john273766764/springboot-mybatis-threads">https://gitee.com/john273766764/springboot-mybatis-threads</a>
 * </p>
 *
 * @author wenchangwei
 * @since 2022/8/28 9:06 PM
 */
@Slf4j
@Component
public class UserComponent {

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private UserDao userDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Transactional(rollbackFor = {Exception.class})
    public void batchAdd(List<UserEntity> userEntityList, List<UserInfoEntity> infoEntityList,
                         List<TransactionStatus> transactionStatuses, int finalI, int number) {
        log.info("子线程：" + Thread.currentThread().getName());
        // 使用这种方式将事务状态都放在同一个事务里面
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务，这样会比较安全些。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获得事务状态
        TransactionStatus status = transactionManager.getTransaction(def);
        transactionStatuses.add(status);

        if (Objects.equals(finalI, number)) {
            throw new RuntimeException("出现事务异常");
        }

        userDao.batchSaveUser(userEntityList);
        userInfoDao.batchSaveUserInfo(infoEntityList);
    }

}
