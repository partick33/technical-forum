package com.partick.forum.job.config;

import com.partick.forum.job.jobs.ArticleDetailJob;
import com.partick.forum.job.jobs.ArticleInfoJob;
import com.partick.forum.job.jobs.ForumCategoryJob;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author partick_peng
 */
@Configuration
public class QuartzConfig {

    @Resource
    private Scheduler scheduler;

    @PostConstruct
    public void getScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void setScheduleJob() {
        try {
            setScheduleJob("ForumCategoryJob",
                    "ForumCategoryGroupJob",
                    "0 0 0 1/1 * ? *",
                    ForumCategoryJob.class);
            setScheduleJob("ArticleInfoJob",
                    "ArticleInfoGroupJob",
                    "0 0 0/2 * * ? * ",
                    ArticleInfoJob.class);
            setScheduleJob("ArticleDetailJob",
                    "ArticleDetailGroupJob",
                    "0 0 0/4 * * ? *",
                    ArticleDetailJob.class);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void setScheduleJob(String jobName,
                                String jobGroupName,
                                String corn, Class<? extends Job> cls) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(cls)
                .withIdentity(jobName, jobGroupName)
                .storeDurably()
                .build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(TriggerKey.triggerKey(jobName, jobGroupName))
                .withSchedule(CronScheduleBuilder.cronSchedule(corn))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
