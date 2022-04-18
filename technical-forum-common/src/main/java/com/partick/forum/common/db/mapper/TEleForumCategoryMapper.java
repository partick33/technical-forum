package com.partick.forum.common.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.partick.forum.common.db.pojo.TEleForumCategory;
import com.partick.forum.common.vo.ForumCategoryVO;

import java.util.List;

/**
 * @Entity com.partick.forum.common.db.pojo.TEleForumCategory
 */
public interface TEleForumCategoryMapper extends BaseMapper<TEleForumCategory> {

    /**
     * 查询论坛分类信息根据分类id
     * @param categoryId
     * @return
     */
    List<ForumCategoryVO> queryByCategoryId(String categoryId);

    /**
     * 查询论坛分类信息根据分论坛id
     * @param forumId
     * @return
     */
    List<ForumCategoryVO> queryByForumId(String forumId);
}




