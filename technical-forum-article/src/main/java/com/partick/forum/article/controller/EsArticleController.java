package com.partick.forum.article.controller;

import com.partick.forum.article.service.EsArticleService;
import com.partick.forum.common.config.CommonResult;
import com.partick.forum.common.elasticsearch.pojo.EsArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author partick_peng
 */
@RestController
@RequestMapping("/esArticle")
public class EsArticleController {

    @Autowired
    private EsArticleService esArticleService;

    @GetMapping("/putBulkEsArticle")
    public CommonResult putBulkEsArticle() {
        return new CommonResult(esArticleService.putBulkEsArticle(), "推送到ES");
    }

    @GetMapping("/queryLikeTitle")
    public CommonResult queryLikeTitle(@RequestParam("title") String title) {
        List<EsArticle> esArticles = esArticleService.queryLikeTitle(title);
        return new CommonResult(true, "搜索成功", esArticles);
    }

    @GetMapping("/queryTitleKeyWordCount")
    public CommonResult queryTitleKeyWordCount() {
        return new CommonResult(true, "获取数据成功", esArticleService.queryTitleKeyWordCount());
    }

    @GetMapping("/queryEsArticleCountByForum")
    public CommonResult queryEsArticleCountByForum() {
        return new CommonResult(true, "获取数据成功", esArticleService.queryEsArticleCountByForum());
    }
}
