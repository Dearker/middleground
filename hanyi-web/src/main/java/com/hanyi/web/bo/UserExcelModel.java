package com.hanyi.web.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户导出实体类
 * </p>
 *
 * @author wenchangwei
 * @since 10:39 上午 2020/12/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExcelModel implements Serializable {

    private static final long serialVersionUID = 168156774663538699L;

    @ExcelProperty(value = "用户名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ExcelProperty(value = "手机号", index = 2)
    private String mobile;

    @ExcelProperty(value = "性别", index = 3)
    private String sex;

}
