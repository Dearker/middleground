package com.hanyi.hikari.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wcwei@iflytek.com
 * @since 2020-11-27 17:23
 */
@Data
public class BookQueryParam implements Serializable {

    private static final long serialVersionUID = 8802569022306802637L;

    /**
     * 书籍id
     */
    private Long id;

    /**
     * 书籍名称
     */
    private String bookName;

    /**
     * 书籍标题
     */
    private String bookTitle;

    /**
     * 书籍类型
     */
    private Integer bookType;

    /**
     * 书籍总数
     */
    private Integer bookTotal;

}
