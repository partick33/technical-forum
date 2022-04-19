package com.partick.forum.article.service;

import com.partick.forum.common.elasticsearch.pojo.EsArticle;

import java.util.List;

/**
 * @author partick_peng
 */
public interface EsArticleService {

    /**
     * 批量推送文章概要信息到Es
     * @return
     */
    Boolean putBulkEsArticle();

    /**
     * 模糊搜索标题
     * @param title
     * @return
     */
    List<EsArticle> queryLikeTitle(String title);
}
