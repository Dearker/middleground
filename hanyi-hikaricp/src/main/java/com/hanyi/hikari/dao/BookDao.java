package com.hanyi.hikari.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hanyi.hikari.pojo.Book;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:54 下午 2020/6/8
 */
public interface BookDao extends BaseMapper<Book> {

    /**
     * 批量插入
     *
     * @param bookList 插入的集合
     * @return 返回插入条数
     */
    int batchInsertBook(List<Book> bookList);

    /**
     * 分页查询类型数据
     *
     * @param page     分页参数
     * @param bookType 查询条件
     * @return 返回分页结果
     */
    IPage<Book> selectBookPage(Page<?> page, Integer bookType);

}
