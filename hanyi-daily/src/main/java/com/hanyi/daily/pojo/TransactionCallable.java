package com.hanyi.daily.pojo;

import com.hanyi.framework.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @author wcwei@iflytek.com
 * @since 2021-09-27 18:07
 */
@Slf4j
@RequiredArgsConstructor
public class TransactionCallable implements Callable<Object> {

    private final PlatformTransactionManager platformTransactionManager;
    private final Callable<Object> supplier;

    @Override
    public Object call() {
        if (Objects.isNull(platformTransactionManager)) {
            throw new BizException("参数异常");
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = this.platformTransactionManager.getTransaction(def);
        Object result = null;
        try {
            result = this.supplier.call();
            platformTransactionManager.commit(transactionStatus);
            log.info("【多线程事务-子线程】事务回滚");
        } catch (Exception e) {
            log.error("【多线程事务】执行失败: ", e);
            platformTransactionManager.rollback(transactionStatus);
        }

        return result;
    }
}
