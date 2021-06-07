package com.example.demo.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消费者广播消费
 */
@Component
public class RocketConsumerTwo implements MessageListenerConcurrently {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketConsumerOne.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt msg : list) {
            String keys = msg.getKeys();
            String body = new String(msg.getBody());
            LOGGER.info("RocketConsumerTwo :keys:{},body:{}", keys, body);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
