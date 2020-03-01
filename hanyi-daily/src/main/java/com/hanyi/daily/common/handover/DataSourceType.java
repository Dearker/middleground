package com.hanyi.daily.common.handover;

import com.hanyi.daily.common.enums.DataType;

/**
 * @author weiwen
 */
public class DataSourceType {

    /**
     * 使用ThreadLocal保证线程安全
     */
    private static final ThreadLocal<DataType> TYPE = new ThreadLocal<>();

    /**
     * 往当前线程里设置数据源类型
      * @param dataType
     */
    public static void setDataBaseType(DataType dataType) {
        if (dataType == null) {
            throw new NullPointerException();
        }
        TYPE.set(dataType);
    }

    /**
     * 获取数据源类型
     * @return
     */
    static DataType getDataBaseType() {
        return TYPE.get() == null ? DataType.PRIMARY : TYPE.get();
    }

    /**
     * 清空数据类型
     */
    public static void clearDataBaseType() {
        TYPE.remove();
    }

}
