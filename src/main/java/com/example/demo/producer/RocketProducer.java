package com.example.demo.producer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketProducer {

    public static final Logger LOGGER = LoggerFactory.getLogger(RocketProducer.class);

    public RocketProducer(String producerGroup, String address) {
        init(producerGroup, address);
    }

    private static DefaultMQProducer defaultMQProducer;


    /**
     * 在bean创建的时候，就执行init方法
     */
    public void init(String producerGroup, String address) {
        LOGGER.info("start picture rocketMq producer!");
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(address);
        defaultMQProducer.setRetryTimesWhenSendFailed(1);
        defaultMQProducer.setSendMsgTimeout(1000);
        defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(true);
        try {
            defaultMQProducer.start();
            LOGGER.info("picture rocketMq producer start success!");
        } catch (MQClientException e) {
            defaultMQProducer.shutdown();
            LOGGER.error(" RocketMqPictureProducer.init() error :{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生产者生产方法
     *
     * @param topic
     * @param tags
     * @param keys
     * @param body
     */
    public void produce(String topic, String tags, String keys, byte[] body) {
        Message msg = new Message(topic, tags, keys, body);
        try {
            SendResult result = defaultMQProducer.send(msg);
            if (!SendStatus.SEND_OK.equals(result.getSendStatus())) {
                LOGGER.error("thread id:{},group RocketMqPictureProducer.produce() sucess!messageBody:{},status:{}", Thread.currentThread().getId(), body, result.getSendStatus());
            }
            LOGGER.info("RocketMqPictureProducer.produce() send MQ status==> msgId:{}, key:{}, status:{}", result.getMsgId(), msg.getKeys(), result.getSendStatus());
        } catch (Exception e) {
            LOGGER.error("group RocketMqPictureProducer.produce() fail!messageBody:{}", body);
            LOGGER.error("e:{}",ExceptionUtils.getStackTrace(e));
        }
    }
    /**
     * mq默认的：messageDelayLevel：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @param topic
     * @param tag
     * @param key
     * @param body
     * @param delayTimeLevel 1-18分别对应上面的时间
     */
    public void send(String topic, String tag, String key, String body, int delayTimeLevel) {
        try {
            Message msg = new Message(topic, tag, key, body.getBytes("utf-8"));
            msg.setDelayTimeLevel(delayTimeLevel);
            SendResult result = defaultMQProducer.send(msg);
            if (!SendStatus.SEND_OK.equals(result.getSendStatus())) {
                LOGGER.error("thread id:{},group RocketMqPictureProducer.produce() sucess!messageBody:{},status:{}", Thread.currentThread().getId(), body, result.getSendStatus());
            }
            LOGGER.info("RocketMqPictureProducer send MQ status==> msgId:{}, key:{}, status:{}", result.getMsgId(), msg.getKeys(), result.getSendStatus());
        } catch (Exception e) {
            LOGGER.error("group RocketMqPictureProducer.produce() fail!messageBody:{}", body);
        }
    }

}

