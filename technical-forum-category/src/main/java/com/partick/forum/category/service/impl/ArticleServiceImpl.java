package com.partick.forum.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.partick.forum.category.jsoup.ArticleFromJsoup;
import com.partick.forum.category.service.ArticleService;
import com.partick.forum.common.threadpool.ForumCategoryThreadPool;
import com.partick.forum.common.db.mapper.TCategoryMapper;
import com.partick.forum.common.db.mapper.TEleForumCategoryMapper;
import com.partick.forum.common.db.pojo.TEleForumCategory;
import com.partick.forum.common.mongodb.mapper.ArticleDetailMapper;
import com.partick.forum.common.mongodb.mapper.ArticleInfoMapper;
import com.partick.forum.common.mongodb.pojo.ArticleDetail;
import com.partick.forum.common.mongodb.pojo.ArticleInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author partick_peng
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleFromJsoup articleFromJsoup;

    @Resource
    private TCategoryMapper categoryMapper;

    @Resource
    private TEleForumCategoryMapper eleForumCategoryMapper;

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Resource
    private ArticleDetailMapper articleDetailMapper;

    @Resource
    private ForumCategoryThreadPool forumCategoryThreadPool;

    /**
     * 爬取文章信息
     */
    @Override
    public void putArticleInfo() {
        try {
            putArticleInfo("开源中国", "博客");
            putArticleInfo("思否", "专栏");
            putArticleInfo("博客园", "分类");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void putArticleInfoToMongodb(String forumName, String categoryId, String categoryName,
                                        String forumCategoryId,String forumCategoryName, String url) {
        switch (forumName) {
            case "开源中国":
                List<ArticleInfo> oschinaArticleFromJsoup =
                        articleFromJsoup.getOschinaArticleInfoFromJsoup(url);
                this.putArticleInfoListToMongodb(forumName, categoryId,
                        categoryName, forumCategoryId,forumCategoryName, oschinaArticleFromJsoup);
                break;
            case "思否":
                List<ArticleInfo> segmentFaultArticleFromJsoup =
                        articleFromJsoup.getSegmentFaultArticleInfoFromJsoup(url);
                this.putArticleInfoListToMongodb(forumName, categoryId,
                        categoryName, forumCategoryId,forumCategoryName, segmentFaultArticleFromJsoup);
                break;
            case "博客园":
                List<ArticleInfo> cnBlogsFaultArticleFromJsoup =
                        articleFromJsoup.getCnBlogsArticleInfoFromJsoup(url);
                this.putArticleInfoListToMongodb(forumName, categoryId,
                        categoryName, forumCategoryId,forumCategoryName, cnBlogsFaultArticleFromJsoup);
                break;
            default:
                break;
        }
    }

    /**
     * 爬取文章详细信息
     */
    @Override
    public void putArticleDetail() {
        putArticleDetail("开源中国", "博客");
        putArticleDetail("思否", "专栏");
        putArticleDetail("博客园", "分类");
    }

    /**
     * 查询爬取文章信息写入MongoDB
     *
     * @param forumName
     * @param articleInfoId
     * @param title
     * @param url
     */
    @Override
    public void putArticleDetailToMongodb(String forumName, String articleInfoId, String title, String url) {
        switch (forumName) {
            case "开源中国":
                ArticleDetail oschinaArticleDetailFromJsoup = articleFromJsoup.getOschinaArticleDetailFromJsoup(url);
                putArticleDetailToMongodb(articleInfoId, title, url, oschinaArticleDetailFromJsoup);
                break;
            case "思否":
                ArticleDetail segmentFaultArticleDetailFromJsoup = articleFromJsoup.getSegmentFaultArticleDetailFromJsoup(url);
                putArticleDetailToMongodb(articleInfoId, title, url, segmentFaultArticleDetailFromJsoup);
                break;
            case "博客园":
                ArticleDetail cnBlogsArticleDetailFromJsoup = articleFromJsoup.getCnBlogsArticleDetailFromJsoup(url);
                putArticleDetailToMongodb(articleInfoId, title, url, cnBlogsArticleDetailFromJsoup);
                break;
            default:
                break;
        }
    }

    /**
     * 查询文章信息通过分类
     *
     * @return
     */
    @Override
    public List<ArticleInfo> selectByForumNavId(String forumNavId) {
        return articleInfoMapper.queryByForumNavId(forumNavId);
    }

    /**
     * 通过id查询文章详细信息
     *
     * @param id
     * @return
     */
    @Override
    public ArticleDetail selectDetailByArticleInfoId(String id) {
        return articleDetailMapper.queryByArticleInfoId(id);
    }


    private void putArticleInfo(String forumName, String categoryName) {
        String categoryId = categoryMapper
                .queryCategoryIdByForumNameAndCategoryName(forumName, categoryName);
        List<TEleForumCategory> forumCategories = eleForumCategoryMapper
                .selectList(new QueryWrapper<TEleForumCategory>().eq("category_id", categoryId));
        ThreadPoolTaskExecutor executor = forumCategoryThreadPool.articleInfoExecutor();
        forumCategories.forEach(o -> {
            executor.submit(() -> {
                putArticleInfoToMongodb(forumName, categoryId, categoryName, o.getForumNavId(),o.getForumNav(), o.getFinalUrl());
            });
        });
    }

    private void putArticleInfoListToMongodb(String forumName, String categoryId, String categoryName,
                                             String forumCategoryId,String forumCategoryName, List<ArticleInfo> articleInfoList) {
        articleInfoList.forEach(a -> {
            ArticleInfo articleInfo = articleInfoMapper.queryByTitleAndDate(a.getUrl(),forumCategoryId);
            if (articleInfo != null) {
                throw new RuntimeException("当天已经存在相同的文章概要");
            }
            a.setForumName(forumName);
            a.setCategoryId(categoryId);
            a.setCategoryName(categoryName);
            a.setForumCategoryName(forumCategoryName);
            a.setForumCategoryId(forumCategoryId);
            articleInfoMapper.insert(a);
        });
    }

    private void putArticleDetail(String forumName, String categoryName) {
        List<ArticleInfo> articleInfoList = articleInfoMapper
                .queryByForumNameAndCategoryName(forumName, categoryName);
        ThreadPoolTaskExecutor executor = forumCategoryThreadPool.articleDetailExecutor();
        articleInfoList.forEach(a -> {
            executor.submit(() -> {
                        ArticleDetail articleDetail1 = articleDetailMapper.queryByArticleIdAndDate(a.getArticleInfoId());
                        if (articleDetail1 != null) {
                            throw new RuntimeException("今天存在相同的文章详情");
                        }
                        putArticleDetailToMongodb(forumName,
                                a.getArticleInfoId(), a.getTitle(), a.getUrl());
                    }
            );
        });
    }

    private void putArticleDetailToMongodb(String articleInfoId, String title, String url, ArticleDetail articleDetail) {
        articleDetail.setArticleInfoId(articleInfoId);
        articleDetail.setTitle(title);
        articleDetail.setUrl(url);
        articleDetailMapper.insert(articleDetail);
        articleDetail = null;
    }
}
