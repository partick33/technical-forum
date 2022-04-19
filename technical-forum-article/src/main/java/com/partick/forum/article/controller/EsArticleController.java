package com.partick.forum.article.controller;

import com.partick.forum.article.service.EsArticleService;
import com.partick.forum.common.config.CommonResult;
import com.partick.forum.common.elasticsearch.pojo.EsArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/queryLikeTitle/{title}")
    public CommonResult queryLikeTitle(@PathVariable String title) {
        List<EsArticle> esArticles = esArticleService.queryLikeTitle(title);
        return new CommonResult(true, "搜索成功", esArticles);
    }
}
