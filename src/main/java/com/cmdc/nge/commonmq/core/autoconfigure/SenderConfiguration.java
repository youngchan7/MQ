package com.cmdc.nge.commonmq.core.autoconfigure;

import com.cmdc.nge.commonmq.core.provider.RabbitSender;
import com.cmdc.nge.commonmq.core.sender.Sender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxing
 * @create 2020/4/8
 */
@Configuration
public class SenderConfiguration {

    @Configuration
    @ConditionalOnClass(RabbitTemplate.class)
    public static class RabbitConfiguration{

        @Bean
        @ConditionalOnMissingBean
        public Sender rabbitSender(){
            return new RabbitSender();
        }

    }

}