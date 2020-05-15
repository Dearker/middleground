package com.hanyi.mongo.service;

import com.hanyi.mongo.pojo.ProcInfo;
import com.hanyi.mongo.pojo.UnwindProcInfo;

import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.service
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-14 21:51
 * @Version: 1.0
 */
public interface ProcInfoService {

    /**
     * 查询
     *
     * @return
     */
    List<UnwindProcInfo> findProInfoByCondition();

}
