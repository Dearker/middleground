package com.hanyi.mongo.repository;

import com.hanyi.mongo.pojo.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @PackAge: middleground com.hanyi.mongo.repository
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 22:42
 * @Version: 1.0
 */
public interface BookRepository extends MongoRepository<Book,Long> {

}
