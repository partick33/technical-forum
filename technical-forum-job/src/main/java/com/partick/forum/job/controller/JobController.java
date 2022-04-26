package com.partick.forum.job.controller;

import com.partick.forum.common.config.CommonResult;
import com.partick.forum.common.vo.QuartzJobVO;
import com.partick.forum.job.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author partick_peng
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private QuartzService quartzService;

    @GetMapping("/queryAllJob")
    public CommonResult queryAllJob() {
        List<QuartzJobVO> list = quartzService.queryAllJob();
        return new CommonResult(true, "获取任务列表成功", list);
    }

    @PostMapping("/updateJob")
    public CommonResult updateJob(@RequestBody QuartzJobVO quartzJobVO) {
        quartzService.updateJob(quartzJobVO.getJobName()
                , quartzJobVO.getJobGroupName(), quartzJobVO.getCron());
        return new CommonResult(true, "更新时间成功");
    }

    @PostMapping("/pauseJob")
    public CommonResult pauseJob(@RequestBody QuartzJobVO quartzJobVO) {
        quartzService.pauseJob(quartzJobVO.getJobName(), quartzJobVO.getJobGroupName());
        return new CommonResult(true, "停止任务成功");
    }

    @PostMapping("/startJob")
    public CommonResult startJob(@RequestBody QuartzJobVO quartzJobVO) {
        quartzService.startJob(quartzJobVO.getJobName(), quartzJobVO.getJobGroupName());
        return new CommonResult(true, "启动任务成功");
    }

}
