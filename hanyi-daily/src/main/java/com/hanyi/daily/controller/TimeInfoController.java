package com.hanyi.daily.controller;

import com.hanyi.daily.pojo.TimeInfo;
import com.hanyi.daily.service.TimeInfoService;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 时间详情控制层
 * </p>
 *
 * @author wenchangwei
 * @since 8:47 下午 2020/10/1
 */
@RestController
@RequestMapping("/time")
public class TimeInfoController {

    /**
     * 时间详情逻辑层
     */
    @Resource
    private TimeInfoService timeInfoService;

    /**
     * 新增时间对象
     *
     * @param timeInfo 时间对象
     * @return 返回新增条数
     */
    @PostMapping("/insert")
    public ResponseResult insert(@RequestBody TimeInfo timeInfo) {
        return ResponseResult.success(timeInfoService.insert(timeInfo));
    }

    /**
     * 查询所有数据
     *
     * @return 返回时间对象集合
     */
    @GetMapping("/findAll")
    public ResponseResult findAll() {
        return ResponseResult.success(timeInfoService.findAll());
    }

    /**
     * 根据id查询时间详情
     *
     * @param id 详情id
     * @return 返回时间对象
     */
    @GetMapping("/findById")
    public ResponseResult findById(Long id) {
        if (Objects.isNull(id)) {
            return ResponseResult.failure(ResultCode.PARAM_IS_INVALID);
        }
        return ResponseResult.success(timeInfoService.findById(id));
    }

    /**
     * 根据条件查询时间集合
     *
     * @param timeInfo 查询对象
     * @return 返回集合
     */
    @PostMapping("/condition")
    public List<TimeInfo> findByCondition(@RequestBody TimeInfo timeInfo){
        return timeInfoService.findByCondition(timeInfo);
    }

}
