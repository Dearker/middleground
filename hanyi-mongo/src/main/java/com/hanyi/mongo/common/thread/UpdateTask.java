package com.hanyi.mongo.common.thread;

import com.hanyi.mongo.pojo.Book;
import com.hanyi.mongo.repository.BookRepository;

import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.common.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 22:37
 * @Version: 1.0
 */
public class UpdateTask implements Runnable {


    private BookRepository bookRepository;

    private List<Book> bookList;

    public UpdateTask(BookRepository bookRepository, List<Book> bookList) {
        this.bookRepository = bookRepository;
        this.bookList = bookList;
    }

    @Override
    public void run() {
        bookRepository.saveAll(bookList);
    }
}
