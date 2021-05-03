package com.hanyi.hikari.vo;

import com.hanyi.hikari.pojo.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 书籍分页查询组合对象
 *
 * @author wcwei@iflytek.com
 * @since 2020-11-27 16:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookPageVo implements Serializable {

    private static final long serialVersionUID = -5337475767409238689L;

    /**
     * 总数
     */
    private long total;

    /**
     * 返回数据集合
     */
    private List<Book> bookList;

}
