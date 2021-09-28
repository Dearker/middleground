package com.hanyi.daily.pojo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wcwei@iflytek.com
 * @since 2021-09-28 14:46
 */
@Slf4j
@RequiredArgsConstructor
public class TransactionRunnable implements Runnable {

    private final TransactionTemplate transactionTemplate;

    private final Runnable taskRunnable;

    @Override
    public void run() {
        Boolean executeResult = transactionTemplate.execute(status -> {
            try {
                taskRunnable.run();
                log.info(" Runnable transaction exec success ");
                return true;
            } catch (Exception e) {
                log.error(" Runnable transaction exec fail exception is :", e);
                status.setRollbackOnly();
                return false;
            }
        });
        log.info(" exec result is : {}",executeResult);

        //无返回值执行事务方法
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try{
                    taskRunnable.run();
                } catch (Exception e){
                    log.error(" Runnable transaction exec fail exception is :", e);
                    transactionStatus.setRollbackOnly();
                }
            }
        });
    }
}
