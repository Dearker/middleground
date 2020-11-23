package com.hanyi.canal.common.component;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hanyi.canal.common.constant.RedisConstant;
import com.hanyi.canal.common.util.EntryUtil;
import com.hanyi.canal.dao.UserDao;
import com.hanyi.canal.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 启动初始化类
 * </p>
 *
 * @author wenchangwei
 * @since 4:19 下午 2020/11/22
 */
@Slf4j
@Component
public class CanalClientComponent implements Runnable {

    @Resource
    private UserDao userDao;

    @Resource
    private CanalConnector canalConnector;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * canal入库方法
     */
    @Scheduled(fixedDelay = 10000)
    @Override
    public void run() {
        //获取指定数量的数据
        int batchSize = 1000;
        //尝试从master那边拉去数据batchSize条记录，有多少取多少
        Message message = canalConnector.getWithoutAck(batchSize);
        long batchId = message.getId();
        try {
            List<Entry> entryList = message.getEntries();
            if (batchId != -1 && CollUtil.isNotEmpty(entryList)) {
                for (Entry entry : entryList) {
                    if (EntryType.ROWDATA == entry.getEntryType()) {
                        this.dataHandle(entry);
                    }
                }
                canalConnector.ack(batchId);
            }
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 数据处理
     *
     * @param entry 数据库对象
     */
    private void dataHandle(Entry entry) throws InvalidProtocolBufferException {
        RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
        CanalEntry.EventType eventType = rowChange.getEventType();
        switch (eventType) {
            case INSERT:
                this.saveInsertSql(rowChange);
                break;
            case UPDATE:
                this.saveUpdateSql(rowChange);
                break;
            case DELETE:
                this.saveDeleteSql(rowChange);
                break;
            default:
                break;
        }
    }

    /**
     * 保存更新语句
     *
     * @param rowChange 数据库对象
     */
    private void saveUpdateSql(RowChange rowChange) {
        List<User> userList = EntryUtil.entryToBean(rowChange, User.class);
        if (CollUtil.isNotEmpty(userList)) {
            //暂时只支持单一主键
            Boolean hasKey = stringRedisTemplate.hasKey(RedisConstant.USER);
            if(hasKey){
                stringRedisTemplate.opsForValue().set(RedisConstant.USER, userList.toString(), 10, TimeUnit.MINUTES);
            }
            userList.forEach(user -> userDao.updateById(user));
        }
    }

    /**
     * 保存删除语句
     *
     * @param rowChange 数据库对象
     */
    private void saveDeleteSql(RowChange rowChange) {
        List<String> keyList = new ArrayList<>(Short.SIZE);
        rowChange.getRowDatasList().forEach(s -> s.getBeforeColumnsList().forEach(k -> {
            if (k.getIsKey()) {
                keyList.add(k.getValue());
            }
        }));

        if (CollUtil.isNotEmpty(keyList)) {
            stringRedisTemplate.delete(RedisConstant.USER);
            userDao.deleteBatchIds(keyList);
        }
    }

    /**
     * 保存插入语句
     *
     * @param rowChange 数据库对象
     */
    private void saveInsertSql(RowChange rowChange) {
        List<User> userList = EntryUtil.entryToBean(rowChange, User.class);
        if (CollUtil.isNotEmpty(userList)) {
            stringRedisTemplate.opsForValue().set(RedisConstant.USER, userList.toString(), 10, TimeUnit.MINUTES);
            userDao.batchInsertUser(userList);
        }
    }

}
