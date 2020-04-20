package com.cmdc.nge.commonmq.core.job;

import com.cmdc.nge.commonmq.core.lock.RedisLock;
import com.cmdc.nge.commonmq.core.provider.PendingMessage;
import com.cmdc.nge.commonmq.core.provider.enums.PendingMessageStatus;
import com.cmdc.nge.commonmq.core.sender.Sender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @author wangxing
 * @create 2020/4/8
 */
@Slf4j
public class SendPendingMessageJob {

    @Autowired
    private ObjectProvider<MongoTemplate> mongoTemplateObjectProvider;
    @Autowired
    private Sender sender;

    @RedisLock
    @Scheduled(cron = "${common-mq.sendPendingMessageJob.cron:0 0/1 * * * ?}")
    public void check() {
        mongoTemplateObjectProvider.forEach(mongoTemplate -> {
            try {
                mongoTemplate.find(Query.query(Criteria.where("status").is(PendingMessageStatus.PENDING).and("createTime").lte(DateUtils.addMinutes(new Date(), -1))), PendingMessage.class)
                        .forEach(pendingMessage -> sender.send(pendingMessage));
            } catch (Exception ignored) {
                log.warn(ignored.getMessage(), ignored);
            }
        });
    }

}