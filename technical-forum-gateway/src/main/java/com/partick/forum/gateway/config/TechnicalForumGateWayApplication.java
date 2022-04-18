package com.partick.forum.gateway.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author partick_peng
 */
@SpringBootApplication
public class TechnicalForumGateWayApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TechnicalForumGateWayApplication.class);
        application.run();
    }
}
