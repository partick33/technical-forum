package com.partick.forum.job.jobs;

import com.partick.forum.job.feign.CategoryFeignService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * @author partick_peng
 */
public class ArticleInfoJob implements Job {

    @Resource
    private CategoryFeignService categoryFeignService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        categoryFeignService.putArticleInfo();
    }
}
