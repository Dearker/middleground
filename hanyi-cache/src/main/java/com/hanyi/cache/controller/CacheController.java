package com.hanyi.cache.controller;

import com.hanyi.cache.entity.Book;
import com.hanyi.cache.service.impl.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.cache.controller
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-12 20:32
 * @Version: 1.0
 */
@RestController
public class CacheController {

    /**
     * 书籍服务
     */
    @Resource
    private BookService bookService;

    @GetMapping("/book")
    @Cacheable(cacheNames = "book-key")
    public List<Book> getBooks() {
        List<Book> bookList = new ArrayList<>();

        Book book = new Book(1, "柯基", "哈士奇");
        bookList.add(book);
        System.out.println("获取的数据--》" + bookList.toString());

        return bookList;
    }

    /**
     * 缓存名称(即分组)，缓存的key,返回的结果不为null才缓存
     *
     * @param book 参数对象
     * @return 返回对象
     */
    @PostMapping("/list")
    @Cacheable(cacheNames = "Book", key = "#book.id + #book.name + #book.author", unless = "#result==null")
    public Book getById(@RequestBody Book book) {
        return book;
    }

    @GetMapping("/find")
    public String find(){
        return bookService.findCacheData();
    }

}
