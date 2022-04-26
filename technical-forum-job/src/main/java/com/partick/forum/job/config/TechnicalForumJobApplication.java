package com.partick.forum.job.config;


import com.partick.forum.common.threadpool.ForumCategoryThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


/**
 * @author partick_peng
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.partick.forum.common","com.partick.forum.job"},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ForumCategoryThreadPool.class),
            @ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.partick.forum.common.mongodb.*")
        }
)
@EnableFeignClients(basePackages = {"com.partick.forum.job.feign"})
public class TechnicalForumJobApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TechnicalForumJobApplication.class);
        application.run();
    }
}
