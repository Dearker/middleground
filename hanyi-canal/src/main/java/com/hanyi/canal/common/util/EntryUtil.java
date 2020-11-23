package com.hanyi.canal.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * entry转换工具类
 *
 * @author wcwei@iflytek.com
 * @since 2020-11-23 14:05
 */
@Slf4j
public class EntryUtil {

    /**
     * 将entry对象转换成对应是实体类
     *
     * @param rowChange rowChange对象
     * @param clazz     实体类class
     * @return 返回实体类集合
     */
    public static <T> List<T> entryToBean(RowChange rowChange, Class<T> clazz) {
        List<RowData> rowDataList = rowChange.getRowDatasList();
        List<T> clazzList = new ArrayList<>(rowDataList.size());
        for (RowData rowData : rowDataList) {
            List<CanalEntry.Column> newColumnList = rowData.getAfterColumnsList();
            Map<String, String> columnMap = newColumnList.stream()
                    .filter(s -> StrUtil.isNotBlank(s.getName()) && StrUtil.isNotBlank(s.getValue()))
                    .collect(Collectors.toMap(CanalEntry.Column::getName, CanalEntry.Column::getValue));
            clazzList.add(BeanUtil.toBean(columnMap, clazz));
        }

        return clazzList;
    }

}
