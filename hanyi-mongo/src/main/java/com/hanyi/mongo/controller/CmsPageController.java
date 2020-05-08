package com.hanyi.mongo.controller;

import com.hanyi.mongo.pojo.CmsPage;
import com.hanyi.mongo.service.PageService;
import com.hanyi.mongo.vo.QueryPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @PackAge: middleground com.hanyi.mongo.controller
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-08 22:10
 * @Version: 1.0
 */
@RestController
public class CmsPageController {

    @Autowired
    private PageService pageService;

    @GetMapping("/list/{page}/{size}")
    public Page<CmsPage> findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return pageService.findList(page, size, queryPageRequest);
    }

    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return pageService.getById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        pageService.update(id, cmsPage);
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable("id") String id) {
        pageService.delete(id);
    }

}
