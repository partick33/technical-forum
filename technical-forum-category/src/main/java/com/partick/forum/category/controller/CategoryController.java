package com.partick.forum.category.controller;

import com.partick.forum.category.service.CategoryService;
import com.partick.forum.common.config.CommonResult;
import com.partick.forum.common.vo.CategoriesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author partick_peng
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public CommonResult createForumCategory(@RequestBody @Valid CategoriesVO categoriesVO) {
        categoryService.createCategory(categoriesVO);
        return new CommonResult(true, "创建论坛信息成功");
    }

    @PostMapping("/del")
    public CommonResult delForumCategory(@RequestBody @Valid CategoriesVO categoriesVO) {
        categoryService.delCategory(categoriesVO);
        return new CommonResult(true, "删除论坛信息成功");
    }

    @GetMapping("/getForumCategoryById/{forumId}")
    public CommonResult getForumCategoryById(@PathVariable String forumId) {
        CategoriesVO categoriesVO = categoryService.getCategoryById(forumId);
        return new CommonResult(true, "获取论坛信息成功", categoriesVO);
    }
}
