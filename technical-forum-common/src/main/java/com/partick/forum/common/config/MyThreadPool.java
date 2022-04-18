package com.partick.forum.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池
 * @author partick_peng
 */
@Configuration
public class MyThreadPool{

    @Bean(name = "articleInfoExecutor")
    public ThreadPoolTaskExecutor articleInfoExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(5000);
        executor.setThreadNamePrefix("articleInfoExecutor-" + Thread.currentThread().getId());
        executor.setAllowCoreThreadTimeOut(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean(name = "articleDetailExecutor")
    public ThreadPoolTaskExecutor articleDetailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(20);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("articleDetailExecutor-" + Thread.currentThread().getId());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
