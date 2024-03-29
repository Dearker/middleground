package com.hanyi.hikari.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author weiwen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_book")
public class Book extends Model<Book> {

    private static final long serialVersionUID = 1L;

    /**
     * 书籍id
     */
    @TableId
    private Long id;

    /**
     * 书籍名称
     */
    private String bookName;

    /**
     * 书籍标题
     */
    private String bookTitle;

    /**
     * 书籍类型
     */
    private Integer bookType;

    /**
     * 书籍总数
     */
    private Integer bookTotal;

    /**
     * 书籍创建时间
     */
    private Date createTime;

}
