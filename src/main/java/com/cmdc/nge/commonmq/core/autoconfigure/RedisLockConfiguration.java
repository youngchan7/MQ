package com.cmdc.nge.commonmq.core.autoconfigure;

import com.cmdc.nge.commonmq.core.lock.RedisLockAspect;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author wangxing
 * @create 2020/4/10
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisLockConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisLockAspect redisLockAspect(ObjectProvider<StringRedisTemplate> redisTemplates) {
        StringRedisTemplate redisTemplate = redisTemplates.getIfUnique();
        return redisTemplate == null ? null : new RedisLockAspect(redisTemplate);
    }

}