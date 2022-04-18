package com.partick.forum.common.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.partick.forum.common.db.pojo.TEleForum;
import com.partick.forum.common.vo.ForumVO;

import java.util.List;

/**
 * @Entity com.partick.forum.common.db.pojo.TEleForum
 */
public interface TEleForumMapper extends BaseMapper<TEleForum> {

    /**
     * 获取论坛列表
     * @return
     */
    List<ForumVO> queryList();
}




