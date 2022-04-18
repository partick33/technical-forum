package com.partick.forum.article.service;

/**
 * @author partick_peng
 */
public interface EsArticleService {

    /**
     * 批量推送文章概要信息到Es
     * @return
     */
    Boolean putBulkEsArticle();
}
