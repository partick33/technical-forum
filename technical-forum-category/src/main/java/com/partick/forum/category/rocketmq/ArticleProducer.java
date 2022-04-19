package com.partick.forum.category.rocketmq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.partick.forum.common.db.pojo.TLocalMessage;
import com.partick.forum.common.utils.RocketMQUtils;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author partick_peng
 */
@Component
public class ArticleProducer {
    @Autowired
    private RocketMQUtils rocketMQUtils;

    @Autowired
    private InsertArticleInfoMessageTransactionListener insertArticleInfoMessageTransactionListener;

    @Autowired
    private UpdateArticleInfoStatusMessageTransactionListener updateArticleInfoStatusMessageTransactionListener;

    /**
     * 发送消息通知从mongodb获取文章概要信息推送到es
     */
    public void sendArticleInfoToEs() {
        try {
            String groupName = "article";
            TransactionMQProducer producer =
                    rocketMQUtils.getTransactionMQProducer(groupName);
            producer.setTransactionListener(insertArticleInfoMessageTransactionListener);
            producer.start();
            Message message = new Message();
            message.setTopic("articleInfo");
            message.setKeys(IdUtil.simpleUUID());
            String body = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:sss") + "更新文章概要信息到es";
            message.setBody(body.getBytes(StandardCharsets.UTF_8));
            producer.sendMessageInTransaction(message, groupName);
            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBackArticleInfoEsSuccess(TLocalMessage localMessage, String articleInfoEsMessageId) {
        try {
            String groupName = "article";
            TransactionMQProducer producer =
                    rocketMQUtils.getTransactionMQProducer(groupName);
            producer.setTransactionListener(updateArticleInfoStatusMessageTransactionListener);
            producer.start();
            Message message = new Message();
            message.setTopic("articleInfo");
            message.setKeys(localMessage.getMessageId());
            message.setBody(articleInfoEsMessageId.getBytes(StandardCharsets.UTF_8));
            localMessage.setMessageCode(12);
            producer.sendMessageInTransaction(message, localMessage);
            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
