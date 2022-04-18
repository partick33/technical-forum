package com.partick.forum.category.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author partick_peng
 */
@SpringBootApplication
@MapperScan("com.partick.forum.common.db.mapper")
@ComponentScan(basePackages = {"com.partick.forum.common","com.partick.forum.category"})
public class TechnicalForumCategoryApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TechnicalForumCategoryApplication.class);
        application.run();
    }
}
