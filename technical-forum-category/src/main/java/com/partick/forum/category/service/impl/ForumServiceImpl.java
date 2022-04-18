package com.partick.forum.category.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.partick.forum.category.service.ForumService;
import com.partick.forum.common.db.mapper.TEleForumMapper;
import com.partick.forum.common.db.pojo.TEleForum;
import com.partick.forum.common.utils.DataBaseDefaultFieldUtil;
import com.partick.forum.common.vo.ForumVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author partick_peng
 */
@Service
public class ForumServiceImpl implements ForumService {

    @Resource
    private TEleForumMapper eleForumMapper;

    /**
     * 创建论坛信息
     *
     * @param tEleForum
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createForum(TEleForum tEleForum) {
        if (eleForumMapper.selectCount(new QueryWrapper<TEleForum>()
                .eq("forum_name", tEleForum.getForumName())) >=1) {
            throw new RuntimeException("论坛名字已存在");
        }
        try {
            DataBaseDefaultFieldUtil.defaultCreateField(tEleForum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tEleForum.setForumId(IdUtil.simpleUUID());
        eleForumMapper.insert(tEleForum);
        tEleForum = null;
    }

    /**
     * 删除论坛信息
     *
     * @param tEleForum
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delForum(TEleForum tEleForum) {
        try {
            DataBaseDefaultFieldUtil.defaultUpdateField(tEleForum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tEleForum.setIsDeleted(1);
        eleForumMapper.update(tEleForum, new UpdateWrapper<TEleForum>()
                .eq("forum_id", tEleForum.getForumId()));
        tEleForum = null;
    }

    /**
     * 获取论坛列表
     *
     * @return
     */
    @Override
    public List<ForumVO> getForums() {
        List<ForumVO> forumVOList = eleForumMapper.queryList();
        return forumVOList;
    }

    /**
     * 通过id获取论坛信息
     * @param forumId
     * @return
     */
    @Override
    public ForumVO getForumsById(String forumId) {
        TEleForum forum = eleForumMapper.selectOne(new QueryWrapper<TEleForum>()
                .eq("forum_id", forumId)
                .eq("is_deleted", 0)
        );

        ForumVO forumVO = new ForumVO();
        BeanUtils.copyProperties(forum, forumVO);
        forum = null;
        return forumVO;
    }


}
