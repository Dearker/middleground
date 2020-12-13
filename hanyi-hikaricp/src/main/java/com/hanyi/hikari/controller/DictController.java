package com.hanyi.hikari.controller;

import com.hanyi.hikari.pojo.DictEntity;
import com.hanyi.hikari.service.DictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典控制层
 * </p>
 *
 * @author wenchangwei
 * @since 9:54 下午 2020/12/11
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private DictService dictService;

    /**
     * 递归获取字典层级集合
     *
     * @return 返回集合
     */
    @GetMapping("/findAll")
    public List<DictEntity> findAllByRecursion() {
        return dictService.findAllByRecursion();
    }

}
