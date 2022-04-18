package com.partick.forum.category.service;

import com.partick.forum.common.db.pojo.TEleForum;
import com.partick.forum.common.vo.ForumVO;

import java.util.List;

/**
 * 论坛服务
 * @author partick_peng
 */
public interface ForumService {

    /**
     * 创建论坛信息
     * @param tEleForum
     */
    void createForum(TEleForum tEleForum);


    /**
     * 删除论坛信息
     * @param tEleForum
     * @return
     */
    void delForum(TEleForum tEleForum);

    /**
     * 获取论坛列表
     * @return
     */
    List<ForumVO> getForums();

    /**
     * 通过id获取论坛信息
     * @param forumId
     * @return
     */
    ForumVO getForumsById(String forumId);

}
