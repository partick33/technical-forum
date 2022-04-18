package com.partick.forum.category.service;

import com.partick.forum.common.vo.ForumCategoriesVO;
import com.partick.forum.common.vo.ForumCategoryVO;

import java.util.List;

/**
 * 论坛分类服务
 * @author partick_peng
 */
public interface ForumCategoryService {

    /**
     * 创建论坛分类信息
     * @param forumCategoriesVO
     */
    void createForumCategory(ForumCategoriesVO forumCategoriesVO);


    /**
     * 删除论坛分类信息
     * @param forumCategoriesVO
     * @return
     */
    void delForumCategory(ForumCategoriesVO forumCategoriesVO);

    /**
     * 通过id获取论坛分类信息
     * @param categoryId
     * @return
     */
    ForumCategoriesVO getForumCategoryById(String categoryId);

    /**
     * 通过论坛id获取论坛分类信息
     * @param forumId
     * @return
     */
    List<ForumCategoryVO> getForumCategoryByForumId(String forumId);

    /**
     * 爬取分类信息同步到mysql
     */
    void putForumCategoryInfo();

}
