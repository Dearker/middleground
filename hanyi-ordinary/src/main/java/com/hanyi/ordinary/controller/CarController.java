package com.hanyi.ordinary.controller;

import com.hanyi.framework.model.response.ResponseResult;
import com.hanyi.ordinary.bo.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2020-10-10 14:46
 */
@Slf4j
@RestController
@RequestMapping("/car")
public class CarController {

    /**
     * 新增车辆信息
     *
     * @param car 车辆对象
     * @return 返回新增结果
     */
    @PostMapping("/insert")
    public ResponseResult insert(@RequestBody Car car){
      log.info(car.toString());
      return ResponseResult.success(car);
    }

}
