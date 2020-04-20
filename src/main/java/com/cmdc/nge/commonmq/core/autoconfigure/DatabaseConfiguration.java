package com.cmdc.nge.commonmq.core.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wangxing
 * @create 2020/4/8
 */
@Configuration
public class DatabaseConfiguration {

    @Configuration
    @ConditionalOnClass(MongoTemplate.class)
    public static class MongoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public PlatformTransactionManager mongoTransactionManager(MongoDbFactory mongoDbFactory) {
            return new MongoTransactionManager(mongoDbFactory);
        }

        @Bean
        @ConditionalOnMissingBean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
            return new TransactionTemplate(transactionManager);
        }

    }

}