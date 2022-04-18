package com.partick.forum.category.service;

import com.partick.forum.common.vo.CategoriesVO;

/**
 * 分类服务
 * @author partick_peng
 */
public interface CategoryService {
    /**
     * 创建分类信息
     * @param categoriesVOS
     */
    void createCategory(CategoriesVO categoriesVOS);


    /**
     * 删除分类信息
     * @param categoriesVOS
     */
    void delCategory(CategoriesVO categoriesVOS);

    /**
     * 通过id获取分类信息
     * @param forumId
     * @return
     */
    CategoriesVO getCategoryById(String forumId);

    /**
     * 获取中间分类路径
     * @param forumName
     * @param categoryName
     * @return
     */
    String getMiddleCategoryUrl(String forumName,String categoryName);

    /**
     * 通过论坛名字和分类名字获取分类id
     * @param forumName
     * @param categoryName
     * @return
     */
    String getCategoryId(String forumName,String categoryName);
}
