package com.cmdc.nge.commonmq.core.persistence;

import com.cmdc.nge.commonmq.core.consumer.ConsumedMessage;
import com.cmdc.nge.commonmq.core.provider.PendingMessage;
import com.cmdc.nge.commonmq.core.provider.enums.PendingMessageStatus;

import java.util.List;

/**
 * @author wangxing
 * @create 2020/4/8
 */
public interface Persistence {

    void save(List<PendingMessage> pendingMessages);

    void save(ConsumedMessage consumedMessage);

    void changePendingMessageStatus(String id, PendingMessageStatus status);

    List<PendingMessage> getPendingMessages();

}
