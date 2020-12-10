package com.hanyi.rocket.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息日志实体类
 * </p>
 *
 * @author wenchangwei
 * @since 9:44 下午 2020/12/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tx_log")
public class TransactionLog implements Serializable {

    private static final long serialVersionUID = 32152258094374191L;

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 消息体
     */
    private String message;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
