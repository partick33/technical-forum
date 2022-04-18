package com.partick.forum.category.controller;

import com.partick.forum.category.service.ForumService;
import com.partick.forum.common.config.CommonResult;
import com.partick.forum.common.db.pojo.TEleForum;
import com.partick.forum.common.vo.ForumVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author partick_peng
 */
@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @PostMapping("/create")
    public CommonResult createForum(@RequestBody @Valid ForumVO forumVO) {
        TEleForum eleForum = new TEleForum();
        BeanUtils.copyProperties(forumVO, eleForum);
        forumService.createForum(eleForum);
        return new CommonResult(true, "创建论坛信息成功");
    }

    @PostMapping("/del")
    public CommonResult delForum(@RequestBody @Valid ForumVO forumVO) {
        TEleForum eleForum = new TEleForum();
        BeanUtils.copyProperties(forumVO, eleForum);
        forumService.delForum(eleForum);
        return new CommonResult(true, "删除论坛信息成功");
    }

    @GetMapping("/getForums")
    public CommonResult getForums() {
        List<ForumVO> forums = forumService.getForums();
        return new CommonResult(true, "获取论坛列表成功", forums);
    }

    @GetMapping("/getForumById/{forumId}")
    public CommonResult getForumById(@PathVariable String forumId) {
        ForumVO forumVO = forumService.getForumsById(forumId);
        return new CommonResult(true, "获取论坛信息成功", forumVO);
    }
}
