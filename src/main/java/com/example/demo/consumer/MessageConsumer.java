package com.example.demo.consumer;

import com.example.demo.config.GetApplicationConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消费者配置
 * @author wcy
 */
@Component
public class MessageConsumer {


    public static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private GetApplicationConfig getConfig;
    @Autowired
    private RocketConsumerOne rocketConsumerOne;
    @Autowired
    private RocketConsumerTwo rocketConsumerTwo;


    @PostConstruct
    public void getFirstRegionMessageListener() {

        startMQConsumer(getConfig.pictureAddress, getConfig.pictureMessageGroupName, getConfig.pictureMessageConsumeThreadMin, getConfig.pictureMessageConsumeThreadMax
                , getConfig.pictureMessageTopic, getConfig.pictureMessageTag, getConfig.pictureMessageConsumeMessageBatchMaxSize,
                rocketConsumerOne);
        startMQConsumer(getConfig.pushAddress, getConfig.pushMessageGroupName, getConfig.pushMessageConsumeThreadMin, getConfig.pushMessageConsumeThreadMax
                , getConfig.pushMessageTopic, getConfig.pushMessageTag, getConfig.pushMessageConsumeMessageBatchMaxSize,
                rocketConsumerTwo);
    }


    public void startMQConsumer(String address, String groupName, int consumeThreadMin, int consumeThreadMax
            , String topic, String tag, int consumeMessageBatchMaxSize, MessageListenerConcurrently messageListenerConcurrently) {
        LOGGER.info("startMQConsumer:{},{},{},{},{},{},{}",address,groupName,consumeThreadMin,consumeThreadMax,
                topic,tag,consumeMessageBatchMaxSize);
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(address);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.registerMessageListener(messageListenerConcurrently);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * 设置广播消费
         */
        consumer.setMessageModel(MessageModel.BROADCASTING);
        /**
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        try {

            consumer.subscribe(topic, tag);
            //设置instance name ，避免创建的消费者被覆盖,用组名称代替
            consumer.setInstanceName(groupName);
            consumer.start();
        } catch (Exception e) {
            LOGGER.error("convert to json obj error:{}", ExceptionUtils.getStackTrace(e));
        }

    }


}
