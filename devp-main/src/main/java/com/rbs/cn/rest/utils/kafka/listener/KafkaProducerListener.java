/*
 * File: KafkaProducerListener.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-28
 */

package com.rbs.cn.rest.utils.kafka.listener;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;


/**
 * @author fengtao.xue
 */
public class KafkaProducerListener implements ProducerListener<String, String> {
    static Logger logger = LoggerFactory.getLogger(KafkaProducerListener.class);

    @Override
    public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
        logger.info("发送kafka消息成功……topic={},partition={},key={},value={},recordMetadata={}", topic, partition, key,
                value, recordMetadata);
    }

    @Override
    public void onError(String topic, Integer partition, String key, String value, Exception e) {
        logger.error("发送kafka消息失败……topic={},partition={},key={},value={},Exception={}", topic, partition,
                key, value, e.getLocalizedMessage());
    }

    @Override
    public boolean isInterestedInSuccess() {
        logger.info("启动kafka producer监听器.....");
        return false;
    }
}
