package com.hanyi.mongo.common.thread;

import com.hanyi.mongo.pojo.Book;
import com.hanyi.mongo.repository.BookRepository;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @PackAge: middleground com.hanyi.mongo.common.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 22:37
 * @Version: 1.0
 */
public class UpdateTask implements Callable<Integer> {

    private BookRepository bookRepository;

    private List<Book> bookList;

    public UpdateTask(BookRepository bookRepository, List<Book> bookList) {
        this.bookRepository = bookRepository;
        this.bookList = bookList;
    }

    @Override
    public Integer call() throws Exception {
        bookRepository.saveAll(bookList);
        return 1;
    }
}
