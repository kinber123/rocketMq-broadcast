package com.example.demo.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yilian.meng
 * @date
 **/
@Configuration
public class RocketMqProducer {

    public static final Logger LOGGER = LoggerFactory.getLogger(RocketMqProducer.class);

    @Bean(name = "rocketProducer")
    @Autowired
    public RocketProducer rocketMqPictureProducer(@Value("${rocketMq.producer.groupName}") String producerGroup,
                                                  @Value("${rocketMq.producer.address}") String address) {
        LOGGER.info("rocketMqProducer:{},{}", producerGroup, address);
        return new RocketProducer(producerGroup, address);
    }

}
