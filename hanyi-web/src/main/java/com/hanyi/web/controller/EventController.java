package com.hanyi.web.controller;

import com.hanyi.web.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 事件控制层
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/1 6:31 下午
 */
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    /**
     * 事件服务
     */
    private final EventService eventService;

    /**
     * 发布事件
     *
     * @return 返回发布结果
     */
    @GetMapping("/publish")
    public String publish() {
        return eventService.publish();
    }

}
