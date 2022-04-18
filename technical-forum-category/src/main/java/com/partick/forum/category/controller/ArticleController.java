package com.partick.forum.category.controller;

import com.partick.forum.category.service.ArticleService;
import com.partick.forum.common.config.CommonResult;
import com.partick.forum.common.mongodb.pojo.ArticleDetail;
import com.partick.forum.common.mongodb.pojo.ArticleInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author partick_peng
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/putArticleInfo")
    public CommonResult putArticleInfo() {
        articleService.putArticleInfo();
        return new CommonResult(true, "推送成功");
    }

    @GetMapping("/putArticleDetail")
    public CommonResult putArticleDetail() {
        articleService.putArticleDetail();
        return new CommonResult(true, "推送成功");
    }

    @GetMapping("/selectByForumNavId/{forumNavId}")
    public CommonResult selectByForumNavId(@PathVariable String forumNavId) {
        List<ArticleInfo> articleInfos =
                articleService.selectByForumNavId(forumNavId);
        return new CommonResult(true, "获取论坛分类名字成功",articleInfos);
    }

    @GetMapping("/selectDetailById/{articleInfoId}")
    public CommonResult selectDetailByArticleInfoId(@PathVariable String articleInfoId) {
        ArticleDetail articleDetail = articleService.selectDetailByArticleInfoId(articleInfoId);
        return new CommonResult(true, "获取文章详细信息成功",articleDetail);
    }
}
