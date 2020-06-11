package com.hanyi.hikari.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.hikari.pojo.Book;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:55 下午 2020/6/8
 */
public interface BookService extends IService<Book> {

    /**
     * 查询书籍集合
     *
     * @return 返回集合
     */
    List<Book> findBookList();

}
