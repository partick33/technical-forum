package com.partick.forum.category.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author partick_peng
 */
@Configuration
public class RedissionConfig {

    @Bean(name = "getRedissonClient")
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        //使用主从模式，提示下如果使用哨兵模式，至少需要两个哨兵节点
        config.useMasterSlaveServers()
                .setMasterAddress("redis://192.168.116.110:6380")
                .addSlaveAddress("redis://192.168.116.109:6380")
                .addSlaveAddress("redis://192.168.116.111:6380")
                .addSlaveAddress("redis://192.168.116.112:6380")
                .setPassword("123456");
        return Redisson.create(config);
    }
}
