package com.hanyi.hikari.controller;

import com.hanyi.hikari.pojo.Book;
import com.hanyi.hikari.service.BookService;
import com.hanyi.hikari.vo.QueryStats;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 书籍控制层
 * </p>
 *
 * @author wenchangwei
 * @since 11:02 下午 2020/6/19
 */
@RestController
@RequestMapping("/")
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping("/queryCount")
    public QueryStats queryCount() {
        return bookService.queryCount();
    }

    @GetMapping("/queryThreadCount")
    public QueryStats queryThreadCount() {
        return bookService.queryThreadCount();
    }

    /**
     * 查询全部的集合
     *
     * @return 返回集合
     */
    @GetMapping("/findAll")
    public List<Book> findAll() {
        return bookService.findBookList();
    }

}
