package com.hanyi.canal.common.component;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hanyi.canal.dao.UserDao;
import com.hanyi.canal.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            for (Entry entry : message.getEntries()) {
                if (EntryType.ROWDATA == entry.getEntryType()) {
                    this.dataHandle(entry);
                }
            }
            canalConnector.ack(batchId);
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
                this.saveInsertSql(entry);
                break;
            case UPDATE:
                this.saveUpdateSql(entry);
                break;
            case DELETE:
                this.saveDeleteSql(entry);
                break;
            default:
                break;
        }
    }

    /**
     * 保存更新语句
     *
     * @param entry 数据库对象
     */
    private void saveUpdateSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDataList = rowChange.getRowDatasList();
            for (RowData rowData : rowDataList) {
                List<CanalEntry.Column> newColumnList = rowData.getAfterColumnsList();
                Map<String, String> columnMap = newColumnList.stream().collect(Collectors.toMap(
                        CanalEntry.Column::getName, CanalEntry.Column::getValue));

                User user = BeanUtil.toBean(columnMap, User.class);
                //暂时只支持单一主键
                userDao.updateById(user);
            }
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 保存删除语句
     *
     * @param entry 数据库对象
     */
    private void saveDeleteSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDataList = rowChange.getRowDatasList();
            for (RowData rowData : rowDataList) {
                List<CanalEntry.Column> columnList = rowData.getBeforeColumnsList();
                for (CanalEntry.Column column : columnList) {
                    if (column.getIsKey()) {
                        //暂时只支持单一主键
                        userDao.deleteById(column.getValue());
                        break;
                    }
                }
            }
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 保存插入语句
     *
     * @param entry 数据库对象
     */
    private void saveInsertSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDataList = rowChange.getRowDatasList();
            for (RowData rowData : rowDataList) {
                List<CanalEntry.Column> columnList = rowData.getAfterColumnsList();

                Map<String, String> columnMap = columnList.stream().collect(Collectors.toMap(
                        CanalEntry.Column::getName, CanalEntry.Column::getValue));

                User user = BeanUtil.toBean(columnMap, User.class);
                userDao.insert(user);
            }
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage());
        }
    }

}
