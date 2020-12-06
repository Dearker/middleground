package com.hanyi.web.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hanyi.web.bo.UserExcelModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户导出监听类
 * </p>
 *
 * @author wenchangwei
 * @since 10:42 上午 2020/12/5
 */
@Slf4j
public class ModelExcelListener extends AnalysisEventListener<UserExcelModel> {

    private final List<UserExcelModel> userExcelModelList = new ArrayList<>(Short.SIZE);

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     *
     * @param userExcelModel  当前对象
     * @param analysisContext 解析器
     */
    @Override
    public void invoke(UserExcelModel userExcelModel, AnalysisContext analysisContext) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        log.info("读取到数据{}", userExcelModelList);
        userExcelModelList.add(userExcelModel);
        //根据业务自行处理，可以写入数据库等等
    }

    /**
     * 所有的数据解析完了调用
     *
     * @param analysisContext 解析器上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成");
    }
}
