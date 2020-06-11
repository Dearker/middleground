package com.hanyi.hikari.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.hikari.dao.BookDao;
import com.hanyi.hikari.pojo.Book;
import com.hanyi.hikari.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:56 下午 2020/6/8
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements BookService {

    @Resource
    private BookDao bookDao;

    @Override
    public List<Book> findBookList() {

        bookDao.selectById(1);

        return null;
    }
}
