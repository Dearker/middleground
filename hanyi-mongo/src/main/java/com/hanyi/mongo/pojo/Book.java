package com.hanyi.mongo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackAge: middleground com.hanyi.mongo.pojo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 11:56
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tb_book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 书籍id
     */
    @Id
    private Long id;

    /**
     * 书籍名称
     */
    @Field("book_name")
    private String bookName;

    /**
     * 书籍标题
     */
    @Field("book_title")
    private String bookTitle;

    /**
     * 书籍类型
     */
    @Field("book_type")
    private Integer bookType;

    /**
     * 书籍总数
     */
    @Field("book_total")
    private int bookTotal;

    /**
     * 书籍创建时间
     */
    @Field("create_time")
    private Date createTime;

}
