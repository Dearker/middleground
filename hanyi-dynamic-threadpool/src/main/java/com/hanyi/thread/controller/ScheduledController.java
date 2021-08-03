package com.hanyi.thread.controller;

import com.hanyi.framework.model.response.ResponseResult;
import com.hanyi.thread.service.ScheduledJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wcwei@iflytek.com
 * @since 2021-08-03 14:50
 */
@RestController
@RequestMapping("/thread")
@RequiredArgsConstructor
public class ScheduledController {

    private final ScheduledJobService scheduledJobService;

    @GetMapping("/threadPoolInfo")
    public ResponseResult threadPoolInfo(){
        return ResponseResult.success(scheduledJobService.getScheduledTaskInfo());
    }

}
