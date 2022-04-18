package com.partick.forum.article.controller;

import com.partick.forum.article.service.EsArticleService;
import com.partick.forum.common.config.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new CommonResult(esArticleService.putBulkEsArticle( ), "推送到ES");
    }
}
