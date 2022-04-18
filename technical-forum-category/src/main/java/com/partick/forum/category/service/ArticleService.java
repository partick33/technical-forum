package com.partick.forum.category.service;

import com.partick.forum.common.mongodb.pojo.ArticleDetail;
import com.partick.forum.common.mongodb.pojo.ArticleInfo;

import java.util.List;

/**
 * @author partick_peng
 */
public interface ArticleService {

    /**
     * 爬取文章信息
     */
    void putArticleInfo();

    /**
     * 查询爬取文章信息写入MongoDB
     *
     * @param forumName
     * @param categoryId
     * @param categoryName
     * @param forumCategoryName
     * @param url
     */
    void putArticleInfoToMongodb(String forumName, String categoryId, String categoryName,
                                 String forumCategoryId, String forumCategoryName, String url);

    /**
     * 爬取文章详细信息
     */
    void putArticleDetail();

    /**
     * 查询爬取文章信息写入MongoDB
     *
     * @param forumName
     * @param articleInfoId
     * @param title
     * @param url
     */
    void putArticleDetailToMongodb(String forumName, String articleInfoId, String title, String url);

    /**
     * 查询文章信息通过论坛分类名字
     *
     * @param forumNav
     * @return
     */
    List<ArticleInfo> selectByForumNavId(String forumNav);

    /**
     * 通过id查询文章详细信息
     * @param id
     * @return
     */
    ArticleDetail selectDetailByArticleInfoId(String id);
}
