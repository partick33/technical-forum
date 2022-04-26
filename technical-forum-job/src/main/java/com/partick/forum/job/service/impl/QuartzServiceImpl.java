package com.partick.forum.job.service.impl;

import cn.hutool.core.util.IdUtil;
import com.partick.forum.common.vo.QuartzJobVO;
import com.partick.forum.job.service.QuartzService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author partick_peng
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    @Resource
    private Scheduler scheduler;

    /**
     * 修改一个任务job
     *
     * @param jobName
     * @param jobGroupName
     * @param corn
     */
    @Override
    public void updateJob(String jobName, String jobGroupName, String corn) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(corn)).build();
            //重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<QuartzJobVO> queryAllJob() {
        ArrayList<QuartzJobVO> jobList = new ArrayList<>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    QuartzJobVO jobVO = new QuartzJobVO();
                    jobVO.setKey(IdUtil.fastSimpleUUID());
                    jobVO.setJobName(jobKey.getName());
                    jobVO.setJobGroupName(jobKey.getGroup());
                    jobVO.setDescription(trigger.getDescription());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    jobVO.setJobStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        jobVO.setCron(((CronTrigger) trigger).getCronExpression());
                    }
                    jobList.add(jobVO);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 暂停任务
     *
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动任务
     *
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void startJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
