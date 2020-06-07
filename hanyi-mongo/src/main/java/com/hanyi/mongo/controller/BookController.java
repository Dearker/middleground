package com.hanyi.mongo.controller;

import com.hanyi.mongo.pojo.many.incloud.QueryStats;
import com.hanyi.mongo.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 4:44 下午 2020/6/7
 */
@RestController
@RequestMapping("/")
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping("/threadCount")
    public QueryStats threadCount(){
        return bookService.threadCount();
    }

    @GetMapping("/countExecAsync")
    public QueryStats countExecAsync(){
        return bookService.countExecAsync();
    }

    @GetMapping("/queryCount")
    public QueryStats queryCount(){
        return bookService.queryCount();
    }


}
