package com.partick.forum.job.jobs;

import com.partick.forum.job.feign.CategoryFeignService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author partick_peng
 */
@Component
public class ForumCategoryJob implements Job {

    @Resource
    private CategoryFeignService categoryFeignService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        categoryFeignService.putForumCategoryInfo();
    }
}
