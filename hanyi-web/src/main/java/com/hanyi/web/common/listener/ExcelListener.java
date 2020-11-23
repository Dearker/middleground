package com.hanyi.web.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hanyi.web.bo.Student;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Excel监听器
 * </p>
 *
 * @author wenchangwei
 * @since 9:00 下午 2020/11/23
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener<Student> {

    /**
     * 封装最终的数据
     */
    private static final List<Student> STUDENT_LIST = new ArrayList<>();

    /**
     * 一行一行去读取excel内容
     *
     * @param student         学生对象
     * @param analysisContext 分析上下文
     */
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        log.info("获取的学生对象为：{}", student);
        STUDENT_LIST.add(student);
    }

    /**
     * 读取完成后执行
     *
     * @param analysisContext 分析上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("获取的数据集合：{}", STUDENT_LIST);
    }
}
