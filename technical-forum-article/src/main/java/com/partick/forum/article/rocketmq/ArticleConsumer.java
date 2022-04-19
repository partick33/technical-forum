package com.partick.forum.article.rocketmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.partick.forum.article.service.EsArticleService;
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
public class ArticleConsumer {
    @Autowired
    private RocketMQUtils rocketMQUtils;

    @Resource
    private TLocalMessageMapper localMessageMapper;

    @Resource
    private ArticleEsProducer articleEsProducer;

    @Autowired
    private EsArticleService esArticleService;

    /**
     * 监听接收推送文章概要信息到ES的消息
     */
    @PostConstruct
    public void receiveArticleInfoToEs() {
        try {
            String groupName = "articleInfo";
            DefaultMQPushConsumer consumer =
                    rocketMQUtils.getDefaultMQPushConsumer(groupName);
            consumer.subscribe("articleInfo", "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (MessageExt messageExt : list) {
                        String keys = messageExt.getKeys();
                        Integer articleInfoEs = localMessageMapper
                                .selectCount(new QueryWrapper<TLocalMessage>()
                                        .eq("message", keys).eq("message_type", "articleInfoEs"));
                        if (articleInfoEs == 0) {
                            esArticleService.putBulkEsArticle();
                            articleEsProducer.sendArticleInfoToEsSuccess(keys);
                        }else {
                            Integer articleInfo = localMessageMapper.selectCount(new QueryWrapper<TLocalMessage>()
                                    .eq("message_id", keys)
                                    .eq("message_type", "articleInfo")
                                    .eq("message_code", 12)
                            );
                            if (articleInfo == 1) {
                                TLocalMessage localMessage = localMessageMapper.selectOne(new QueryWrapper<TLocalMessage>()
                                        .eq("message_id", new String(messageExt.getBody()))
                                        .eq("message_type", "articleInfoEs")
                                        .eq("message_code", 1)
                                );
                                localMessage.setMessageCode(12);
                                localMessageMapper.updateById(localMessage);
                            }
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
