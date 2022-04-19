package com.partick.forum.article.rocketmq;

import cn.hutool.core.util.IdUtil;
import com.partick.forum.common.utils.RocketMQUtils;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author partick_peng
 */
@Component
public class ArticleEsProducer {

    @Autowired
    private RocketMQUtils rocketMQUtils;

    @Autowired
    private InsertArticleInfoEsMessageTransactionListener insertArticleInfoEsMessageTransactionListener;

    /**
     * 返回通知成功推送文章概要信息到es
     */
    public void sendArticleInfoToEsSuccess(String messageId) {
        try {
            String groupName = "article";
            TransactionMQProducer producer =
                    rocketMQUtils.getTransactionMQProducer(groupName);
            producer.setTransactionListener(insertArticleInfoEsMessageTransactionListener);
            producer.start();
            Message message = new Message();
            message.setTopic("articleInfoEs");
            message.setKeys(IdUtil.simpleUUID());
            message.setBody(messageId.getBytes(StandardCharsets.UTF_8));
            producer.sendMessageInTransaction(message, groupName);
            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
