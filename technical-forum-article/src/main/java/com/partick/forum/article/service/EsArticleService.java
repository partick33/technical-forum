package com.partick.forum.article.service;

import com.partick.forum.common.elasticsearch.pojo.EsArticle;
import com.partick.forum.common.elasticsearch.pojo.EsArticleCount;
import com.partick.forum.common.elasticsearch.pojo.EsKeyWordCount;

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


    /**
     * 查询标题中出现关键字次数
     * @return
     */
    List<EsKeyWordCount> queryTitleKeyWordCount();

    /**
     * 分组统计每个论坛的文章总数
     * @return
     */
    List<EsArticleCount> queryEsArticleCountByForum();
}
