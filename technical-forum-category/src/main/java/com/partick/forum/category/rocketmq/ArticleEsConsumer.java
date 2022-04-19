package com.partick.forum.category.rocketmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.partick.forum.common.db.mapper.TLocalMessageMapper;
import com.partick.forum.common.db.pojo.TLocalMessage;
import com.partick.forum.common.utils.RocketMQUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author partick_peng
 */
@Component
public class ArticleEsConsumer {
    @Autowired
    private RocketMQUtils rocketMQUtils;

    @Resource
    private TLocalMessageMapper localMessageMapper;

    @Resource
    private ArticleProducer articleProducer;

    /**
     * 监听接收推送文章概要信息到ES的消息
     */
    @PostConstruct
    public void receiveArticleInfoToEsSuccess() {
        try {
            String groupName = "articleInfoEs";
            DefaultMQPushConsumer consumer =
                    rocketMQUtils.getDefaultMQPushConsumer(groupName);
            consumer.subscribe("articleInfoEs", "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (MessageExt messageExt : list) {
                        String articleInfoEsMessageId = messageExt.getKeys();
                        String articleInfoMessageId = new String(messageExt.getBody());
                        TLocalMessage localMessage = localMessageMapper
                                .selectOne(new QueryWrapper<TLocalMessage>()
                                        .eq("message_id", articleInfoMessageId)
                                        .eq("message_type", "articleInfo")
                                        .eq("message_code", 1)
                                );
                        if (localMessage != null) {
                            articleProducer.sendBackArticleInfoEsSuccess(localMessage, articleInfoEsMessageId);
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
