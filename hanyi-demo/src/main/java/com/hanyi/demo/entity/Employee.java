package com.hanyi.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hanyi
 * @since 2019-11-09
 */
@Data
@Accessors(chain = true)
@TableName("tb_employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String lastName;

    private String email;

    private Integer gender;

    private Integer dId;

}
