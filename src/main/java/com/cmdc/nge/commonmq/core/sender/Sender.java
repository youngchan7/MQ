package com.cmdc.nge.commonmq.core.sender;

import com.cmdc.nge.commonmq.core.provider.PendingMessage;

import java.util.List;

/**
 * @author wangxing
 * @create 2020/4/1
 */
public interface Sender {

    void send(PendingMessage pendingMessage);

    void send(List<PendingMessage> pendingMessage);

}
