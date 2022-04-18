package com.partick.forum.category.jsoup;

import com.partick.forum.common.vo.ForumCategoryVO;

import java.util.List;

/**
 * @author partick_peng
 */
public interface ForumCategoryFromJsoup {

    /**
     * 从开源中国获取分类信息
     * @param categoryName
     * @return
     */
    public List<ForumCategoryVO> getOschinaForumCategoryFromJsoup(String categoryName);

    /**
     * 从思否获取分类信息
     * @param categoryName
     * @return
     */
    public List<ForumCategoryVO> getSegmentFaultForumCategoryFromJsoup(String categoryName);

    /**
     * 从博客园获取分类信息
     * @param fileAddr
     * @return
     */
    public List<ForumCategoryVO> getCnBlogsForumCategoryFromJsoup(String fileAddr);
}
