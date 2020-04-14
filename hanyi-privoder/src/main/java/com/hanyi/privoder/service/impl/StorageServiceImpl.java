package com.hanyi.privoder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanyi.common.exception.ExceptionCast;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.privoder.common.constant.CommonConstant;
import com.hanyi.privoder.entity.Storage;
import com.hanyi.privoder.mapper.StorageMapper;
import com.hanyi.privoder.service.StorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @PackAge: middleground com.hanyi.privoder.service
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-14 15:40
 * @Version: 1.0
 */
@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageMapper storageMapper;

    /**
     * 减库存
     *
     * @param commodityCode
     * @param count
     */
    @Override
    public void deduct(String commodityCode, int count) {
        if (CommonConstant.PRODUCT.equals(commodityCode)) {
            //throw new RuntimeException("异常:模拟业务异常:Storage branch exception");
            ExceptionCast.cast(ResultCode.FAILED);
        }

        QueryWrapper<Storage> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Storage().setCommodityCode(commodityCode));
        Storage storage = storageMapper.selectOne(wrapper);
        storage.setCount(storage.getCount() - count);

        storageMapper.updateById(storage);
    }

}
