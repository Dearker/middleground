package com.hanyi.hikari.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/9 9:24 AM
 */
@Data
public class BookVo implements Serializable {

    private static final long serialVersionUID = -8829036254556542095L;

    /**
     * id
     */
    private Long id;

    /**
     * 书名字
     */
    private String bookName;

    /**
     * 书名
     */
    private String bookTitle;

}
