package com.cmdc.nge.commonmq.core.autoconfigure;

import com.cmdc.nge.commonmq.core.job.SendPendingMessageJob;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wangxing
 * @create 2020/4/9
 */
@EnableScheduling
public class SendPendingMessageJobConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SendPendingMessageJob sendPendingMessageJob() {
        return new SendPendingMessageJob();
    }

}