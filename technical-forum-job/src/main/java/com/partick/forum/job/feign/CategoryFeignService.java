package com.partick.forum.job.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 分类远程调用服务
 * @author partick_peng
 */
@FeignClient(value = "forum-category-service")
public interface CategoryFeignService {

    /**
     * 远程调用推送博客三级分类
     * @return
     */
    @GetMapping("/forumCategory/putForumCategoryInfo")
    void putForumCategoryInfo();

    /**
     * 远程调用推送文章概要信息
     */
    @GetMapping("/article/putArticleInfo")
    void putArticleInfo();

    /**
     * 远程调用推送文章详细信息
     */
    @GetMapping("/article/putArticleDetail")
    void putArticleDetail();
}
