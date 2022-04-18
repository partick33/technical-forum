package com.partick.forum.common.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.partick.forum.common.db.pojo.TCategory;
import com.partick.forum.common.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.partick.forum.common.db.pojo.TCategory
 */
public interface TCategoryMapper extends BaseMapper<TCategory> {

    /**
     * 查询分类信息
     * @param forumId
     * @return
     */
    List<CategoryVO> queryByForumId(String forumId);

    /**
     * 根据id查询中间分类路径
     *
     * @param categoryId
     * @return
     */
    String queryMiddleUrlById(String categoryId);

    /**
     * 根据论坛名字查询中间分类路径
     * @param forumName
     * @param categoryName
     * @return
     */
    String queryMiddleUrlByForumNameAndCategoryName(@Param("forumName") String forumName, @Param("categoryName") String categoryName);

    /**
     * 根据论坛名字查询中间分类路径
     * @param forumName
     * @param categoryName
     * @return
     */
    String queryCategoryIdByForumNameAndCategoryName(@Param("forumName") String forumName,@Param("categoryName") String categoryName);
}




