package com.cmdc.nge.commonmq.core.autoconfigure;

import com.alibaba.fastjson.JSONObject;
import com.cmdc.nge.commonmq.core.consumer.handler.DefaultMessageHandlerMethodFactory;
import com.cmdc.nge.commonmq.core.provider.aop.SendMqMessageAspect;
import com.fasterxml.jackson.databind.JavaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wangxing
 * @create 2020/4/8
 */
@Slf4j
@Configuration
@Order(0)
@AutoConfigureAfter({MongoDataAutoConfiguration.class, RabbitAutoConfiguration.class,
        RedisAutoConfiguration.class})
@Import({SenderConfiguration.class, DatabaseConfiguration.class, RedisLockConfiguration.class})
public class CommonMqAutoConfiguration implements RabbitListenerConfigurer {

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean
    public SendMqMessageAspect sendMqMessageAspect() {
        return new SendMqMessageAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter() {
            @Override
            public Object fromMessage(Message message, Object conversionHint) throws MessageConversionException {
                try {
                    return super.fromMessage(message, conversionHint);
                } catch (MessageConversionException messageConversionException) {
                    JavaType javaType =
                            getJavaTypeMapper().toJavaType(message.getMessageProperties());
                    if (String.class.equals(javaType.getRawClass())) {
                        try {
                            return ((JSONObject) JSONObject.parse(message.getBody())).toJSONString();
                        } catch (Exception e) {
                            throw messageConversionException;
                        }
                    } else {
                        throw messageConversionException;
                    }
                }
            }
        };
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(createDefaultMessageHandlerMethodFactory());
    }

    private MessageHandlerMethodFactory createDefaultMessageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory defaultFactory =
                new DefaultMessageHandlerMethodFactory();
        defaultFactory.setBeanFactory(beanFactory);
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new BytesToStringConverter(StandardCharsets.UTF_8));
        defaultFactory.setConversionService(conversionService);
        defaultFactory.afterPropertiesSet();
        return defaultFactory;
    }

    private static class BytesToStringConverter implements Converter<byte[], String> {


        private final Charset charset;

        BytesToStringConverter(Charset charset) {
            this.charset = charset;
        }

        @Override
        public String convert(byte[] source) {
            return new String(source, this.charset);
        }

    }

}