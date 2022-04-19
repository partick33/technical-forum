package com.partick.forum.article.config;


import com.partick.forum.common.threadpool.ForumCategoryThreadPool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


/**
 * @author partick_peng
 */
@SpringBootApplication
@MapperScan("com.partick.forum.common.db.mapper")
@ComponentScan(basePackages = {"com.partick.forum.common","com.partick.forum.article"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = ForumCategoryThreadPool.class))
public class TechnicalForumArticleApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TechnicalForumArticleApplication.class);
        application.run();
    }
}
