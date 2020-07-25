package com.hanyi.daily.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:57 下午 2020/7/22
 */
@Data
public class Company implements Serializable {

    private static final long serialVersionUID = 1616860485074708656L;

    private double plannedSpeed;

    private int count;

}
