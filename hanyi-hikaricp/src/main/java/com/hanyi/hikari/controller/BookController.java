package com.hanyi.hikari.controller;

import com.hanyi.hikari.pojo.Book;
import com.hanyi.hikari.request.BookQueryPageParam;
import com.hanyi.hikari.request.BookQueryParam;
import com.hanyi.hikari.service.BookService;
import com.hanyi.hikari.vo.BookPageVo;
import com.hanyi.hikari.vo.QueryStats;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 分页查询
     *
     * @param bookQueryPageParam 查询对象
     * @return 返回结果
     */
    @PostMapping("/page")
    public BookPageVo findBookByPage(@RequestBody BookQueryPageParam bookQueryPageParam) {
        return bookService.findBookListByPage(bookQueryPageParam);
    }

    /**
     * 根据多条件查询数据集合
     *
     * @param bookQueryParam 请求参数
     * @return 返回集合
     */
    @PostMapping("/condition")
    public List<Book> findBookByCondition(@RequestBody BookQueryParam bookQueryParam) {
        return bookService.findBookListByCondition(bookQueryParam);
    }

    /**
     * 分页查询类型数据
     *
     * @param bookQueryPageParam 查询条件
     * @return 返回分页结果
     */
    @PostMapping("/pageType")
    public BookPageVo findBookByPageType(@RequestBody BookQueryPageParam bookQueryPageParam) {
        return bookService.selectBookPage(bookQueryPageParam);
    }

}
