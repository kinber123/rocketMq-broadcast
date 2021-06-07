package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Package:
 * @ClassName: GetConfig
 * @Author: wcy
 * @Date: 2020/7/31 15:48
 * @Description: 读取application 配置的数据
 */
@Component
public class GetApplicationConfig {

    /**
     * RocketMq 的信息配置
     */
    // 第一个消息队列配置
    @Value("${rocketMq.consumer.one.message.address}")
    public String pictureAddress;
    @Value("${rocketMq.consumer.one.message.groupName}")
    public String pictureMessageGroupName;
    @Value("${rocketMq.consumer.one.message.consumeThreadMin}")
    public int pictureMessageConsumeThreadMin;
    @Value("${rocketMq.consumer.one.message.consumeThreadMax}")
    public int pictureMessageConsumeThreadMax;
    @Value("${rocketMq.consumer.one.message.topic}")
    public String pictureMessageTopic;
    @Value("${rocketMq.consumer.one.message.tag}")
    public String pictureMessageTag;
    @Value("${rocketMq.consumer.one.message.consumeMessageBatchMaxSize}")
    public int pictureMessageConsumeMessageBatchMaxSize;

    // 第二个消息队列配置
    @Value("${rocketMq.consumer.two.message.consumeThreadMin}")
    public int pushMessageConsumeThreadMin;
    @Value("${rocketMq.consumer.two.message.consumeThreadMax}")
    public int pushMessageConsumeThreadMax;
    @Value("${rocketMq.consumer.two.message.topic}")
    public String pushMessageTopic;
    @Value("${rocketMq.consumer.two.message.tag}")
    public String pushMessageTag;
    @Value("${rocketMq.consumer.two.message.consumeMessageBatchMaxSize}")
    public int pushMessageConsumeMessageBatchMaxSize;
    @Value("${rocketMq.consumer.two.message.address}")
    public String pushAddress;
    @Value("${rocketMq.consumer.two.message.groupName}")
    public String pushMessageGroupName;

}
