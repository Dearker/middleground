package com.hanyi.daily.service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/1/15 8:15 上午
 */
public interface MeterService<T> {

    Optional<List<T>> getData(String resourceId);

    void saveData(String resourceId,List<T> resourceList);

}
