package com.partick.forum.common.utils;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

/**
 * @author partick_peng
 */
@Component
public class RocketMQUtils {

    /**
     * 获取普通RocketMQ生产者组
     * @param groupName
     * @return
     */
    public DefaultMQProducer getDefaultMQProducer(String groupName) {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr("192.168.116.108:9876");
        defaultMQProducer.setProducerGroup(groupName);
        return defaultMQProducer;
    }

    /**
     * 获取事务RocketMQ生产者组
     * @param groupName
     * @return
     */
    public TransactionMQProducer getTransactionMQProducer(String groupName) {
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer();
        transactionMQProducer.setNamesrvAddr("192.168.116.108:9876");
        transactionMQProducer.setProducerGroup(groupName);
        return transactionMQProducer;
    }

    /**
     * 获取普通RocketMQ消费推送组
     * @param groupName
     * @return
     */
    public DefaultMQPushConsumer getDefaultMQPushConsumer(String groupName) {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setNamesrvAddr("192.168.116.108:9876");
        defaultMQPushConsumer.setConsumerGroup(groupName);
        //设置为集群模式
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        return defaultMQPushConsumer;
    }
}
