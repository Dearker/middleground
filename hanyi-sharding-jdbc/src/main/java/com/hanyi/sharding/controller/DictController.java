package com.hanyi.sharding.controller;

import com.hanyi.sharding.pojo.Dict;
import com.hanyi.sharding.service.DictService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典控制层
 * </p>
 *
 * @author wenchangwei
 * @since 8:51 下午 2020/12/3
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    /**
     * 字典服务
     */
    @Resource
    private DictService dictService;

    /**
     * 新增字典
     *
     * @param dict 字典对象
     * @return 返回新增条数
     */
    @PostMapping("/insert")
    public int insert(@RequestBody Dict dict) {
        return dictService.insert(dict);
    }

    /**
     * 根据id删除字典对象
     *
     * @param id 主键id
     * @return 返回删除条数
     */
    @GetMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return dictService.deleteById(id);
    }

    /**
     * 查询所有的数据
     *
     * @return 返回结果
     */
    @GetMapping("/all")
    public List<Dict> findAll() {
        return dictService.findAll();
    }

}
