/*
 * File: KafkaMessageHandler.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-28
 */

package com.rbs.cn.rest.utils.kafka.handle;

/**
 * @author fengtao.xue
 */
public interface KafkaMessageHandler {
    /**
     * 业务处理方法
     * @param topic
     * @param businessData
     */
    void handle(String topic, String businessData) throws Exception;
}
