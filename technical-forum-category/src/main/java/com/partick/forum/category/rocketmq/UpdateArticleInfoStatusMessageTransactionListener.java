package com.partick.forum.category.rocketmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.partick.forum.common.db.mapper.TLocalMessageMapper;
import com.partick.forum.common.db.pojo.TLocalMessage;
import com.partick.forum.common.utils.DataBaseDefaultFieldUtil;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

/**
 * @author partick_peng
 */
@Component
public class UpdateArticleInfoStatusMessageTransactionListener implements TransactionListener {

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private TLocalMessageMapper localMessageMapper;

    @Resource
    private DefaultTransactionDefinition transactionDefinition;

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        TransactionStatus transaction = null;
        try {
            transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            transactionDefinition.setTimeout(1000);
            transaction = transactionManager.getTransaction(transactionDefinition);
            DataBaseDefaultFieldUtil.defaultUpdateField(o);
            localMessageMapper.updateById((TLocalMessage) o);
            transactionManager.commit(transaction);
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(transaction);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        Integer count = localMessageMapper.selectCount(
                new QueryWrapper<TLocalMessage>()
                        .eq("message_id", messageExt.getKeys())
                        .eq("message_code", 12)
        );

        if (count == 1) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
