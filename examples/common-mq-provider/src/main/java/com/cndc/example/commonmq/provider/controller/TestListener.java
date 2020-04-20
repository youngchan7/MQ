package com.cndc.example.commonmq.provider.controller;

import com.cmdc.nge.commonmq.core.provider.PendingMessage;
import com.cmdc.nge.commonmq.core.provider.PendingMessageContextHolder;
import com.cmdc.nge.commonmq.core.provider.aop.SendMqMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxing
 * @create 2020/3/23
 */
@Component
@RestController
public class TestListener {

    @SendMqMessage
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/guest/testSend")
    public List<PendingMessage> testSend() throws Exception {
//        String id="f76c6ef9-9c4a-4b5e-a786-a947cf554092";
        String id = UUID.randomUUID().toString();
        TestMessage testMessage = new TestMessage(id, "ffs");

        PendingMessageContextHolder.set(Arrays.asList(
                PendingMessage.builder()
                        .appId("appid")
                        .body(testMessage)
                        .exchange("test-e")
                        .routingKey("test-k")
                        .messageId(id)
                        .build()
        ));
//        PendingMessageContextHolder.addAll(Arrays.asList(
//                PendingMessage.builder()
//                        .appId("appid")
//                        .body(testMessage)
//                        .exchange("test-e")
//                        .routingKey("test-k")
//                        .messageId(id)
//                        .build()
//        ));
//        PendingMessageContextHolder.add(
//                PendingMessage.builder()
//                        .appId("appid")
//                        .body(testMessage)
//                        .exchange("test-e")
//                        .routingKey("test-k")
//                        .messageId(id)
//                        .build()
//        );
        return Arrays.asList(PendingMessage.builder()
                .appId("appid")
                .body(testMessage)
                .exchange("test-e")
                .routingKey("test-k2")
                .messageId(id)
                .build());
    }

}