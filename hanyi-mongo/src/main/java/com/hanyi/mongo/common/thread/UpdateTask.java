package com.hanyi.mongo.common.thread;

import com.hanyi.mongo.pojo.Book;
import com.hanyi.mongo.repository.BookRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @PackAge: middleground com.hanyi.mongo.common.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 22:37
 * @Version: 1.0
 */
@RequiredArgsConstructor
public class UpdateTask implements Callable<Integer> {

    private final BookRepository bookRepository;

    private final List<Book> bookList;

    @Override
    public Integer call() throws Exception {
        bookRepository.saveAll(bookList);
        return 1;
    }
}
