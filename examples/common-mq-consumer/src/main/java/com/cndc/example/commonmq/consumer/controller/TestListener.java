package com.cndc.example.commonmq.consumer.controller;

import com.cmdc.nge.commonmq.core.consumer.aop.RabbitConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxing
 * @create 2020/3/23
 */
@Component
@RestController
public class TestListener {

    @RabbitListener(queues = "test-q10")
    @RabbitConsumer
    public void rabbitListener(TestMessage testMessage) {
        System.out.println("========rabbitListener=========");
        System.out.println(testMessage);
//        throw new RuntimeException("test");
    }

    @RabbitListener(queues = "test-q20")
    @RabbitConsumer
    public void rabbitListener2(String testMessage) {
        System.out.println("========rabbitListener2=========");
        System.out.println(testMessage);
//        throw new RuntimeException("test");
    }

}