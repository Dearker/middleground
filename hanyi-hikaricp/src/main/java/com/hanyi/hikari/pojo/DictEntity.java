package com.hanyi.hikari.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 字典实体类
 * </p>
 *
 * @author wenchangwei
 * @since 9:45 下午 2020/12/11
 */
@Data
@TableName("tb_dict")
public class DictEntity implements Serializable {

    private static final long serialVersionUID = 2022373204565835164L;

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 子id
     */
    private Integer childrenId;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 是否启用
     */
    private Integer openFlag;

    /**
     * 字典集合
     */
    @TableField(exist = false)
    private List<DictEntity> dictEntityList;

}
