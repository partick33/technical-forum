package com.partick.forum.category.controller;

import com.partick.forum.category.annoation.RedissionLock;
import com.partick.forum.category.service.ForumCategoryService;
import com.partick.forum.common.config.CommonResult;
import com.partick.forum.common.vo.ForumCategoriesVO;
import com.partick.forum.common.vo.ForumCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author partick_peng
 */
@RestController
@RequestMapping("/forumCategory")
public class ForumCategoryController {

    @Autowired
    private ForumCategoryService forumCategoryService;

    @PostMapping("/create")
    public CommonResult createForumCategory(@RequestBody @Valid ForumCategoriesVO categoriesVO) {
        forumCategoryService.createForumCategory(categoriesVO);
        return new CommonResult(true, "创建分类论坛信息成功");
    }

    @PostMapping("/del")
    public CommonResult delForumCategory(@RequestBody @Valid ForumCategoriesVO categoriesVO) {
        forumCategoryService.delForumCategory(categoriesVO);
        return new CommonResult(true, "删除分类论坛信息成功");
    }

    @GetMapping("/getForumCategoryById/{categoryId}")
    public CommonResult getForumCategoryById(@PathVariable String categoryId) {
        ForumCategoriesVO forumCategoriesVO = forumCategoryService.getForumCategoryById(categoryId);
        return new CommonResult(true, "获取论坛分类信息成功", forumCategoriesVO);
    }

    @GetMapping("/getForumCategoryByForumId/{forumId}")
    public CommonResult getForumCategoryByForumId(@PathVariable String forumId) {
        List<ForumCategoryVO> forumCategoryVOS = forumCategoryService.getForumCategoryByForumId(forumId);
        return new CommonResult(true, "获取论坛分类信息成功", forumCategoryVOS);
    }

    @GetMapping("/putForumCategoryInfo")
    @RedissionLock(expireTime = 60 * 60 * 24, lockName = "putForumCategoryInfo")
    public CommonResult putForumCategoryInfo() {
        forumCategoryService.putForumCategoryInfo();
        return new CommonResult(true, "爬取分类信息成功");
    }
}
