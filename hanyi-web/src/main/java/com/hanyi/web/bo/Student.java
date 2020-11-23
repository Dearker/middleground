package com.hanyi.web.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 学生实体类
 * </p>
 *
 * @author wenchangwei
 * @since 8:45 下午 2020/11/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = -4297017624589320355L;

    /**
     * 设置表头名称
     */
    @ExcelProperty(value = "学生编号",index = 0)
    private int number;

    /**
     * 设置表头名称
     */
    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;

}
