package com.partick.forum.category.jsoup;

import com.partick.forum.common.mongodb.pojo.ArticleDetail;
import com.partick.forum.common.mongodb.pojo.ArticleInfo;

import java.util.List;

/**
 * @author partick_peng
 */
public interface ArticleFromJsoup {

    /**
     * 从开源中国获取文章信息
     * @param url
     * @return
     */
    List<ArticleInfo> getOschinaArticleInfoFromJsoup(String url);

    /**
     * 从思否获取文章信息
     * @param url
     * @return
     */
    List<ArticleInfo> getSegmentFaultArticleInfoFromJsoup(String url);

    /**
     * 从博客园获取文章信息
     * @param url
     * @return
     */
    List<ArticleInfo> getCnBlogsArticleInfoFromJsoup(String url);

    /**
     * 从开源中国获取文章信息
     * @param url
     * @return
     */
    ArticleDetail getOschinaArticleDetailFromJsoup(String url);

    /**
     * 从思否获取文章信息
     * @param url
     * @return
     */
    ArticleDetail getSegmentFaultArticleDetailFromJsoup(String url);

    /**
     * 从博客园获取文章信息
     * @param url
     * @return
     */
    ArticleDetail getCnBlogsArticleDetailFromJsoup(String url);
}
