package com.example.demo.controller;

import com.example.demo.producer.RocketProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.example.demo.controller
 * @ClassName: RocketMqController
 * @Author: wcy
 * @Date: 2021/1/26 17:11
 * @Description:
 */
@RequestMapping("rocket")
@RestController
public class RocketMqController {

    @Autowired
    private RocketProducer rocketProducer;


    @RequestMapping("/producer")
    public Object producer(String string,String value){
        rocketProducer.produce("viid-push-topic","viid",string,value.getBytes());
        return "成功";
    }

}
