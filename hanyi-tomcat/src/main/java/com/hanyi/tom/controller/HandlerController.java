package com.hanyi.tom.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.hanyi.tom.vo.BookVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/10/23 3:16 下午
 */
@RestController
@RequestMapping
public class HandlerController {

    private static final String NAME = "柯基";

    private static final Snowflake SNOWFLAKE = IdUtil.createSnowflake(1,1);

    @GetMapping("/info")
    public BookVo getInfo() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(5);
        return new BookVo(SNOWFLAKE.nextId(), NAME, LocalDateTime.now());
    }

}
