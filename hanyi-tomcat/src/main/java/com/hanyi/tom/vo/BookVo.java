package com.hanyi.tom.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/10/23 3:17 下午
 */
@Data
@AllArgsConstructor
public class BookVo {

    private Long id;

    private String name;

    private LocalDateTime createTime;

}
