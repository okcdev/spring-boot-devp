/*
 * File: KafkaProducer.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-24
 */

package com.rbs.cn.rest.utils.kafka;

import com.rbs.cn.rest.biz.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


/**
 * @author fengtao.xue
 */
@Component
public class KafkaProducer {
    static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public User send(String topic, String key, User data){
        ListenableFuture future = kafkaTemplate.send(topic, key, data.toString());
        future.addCallback(o -> System.out.println("send-消息发送成功：" + data), throwable -> System.out.println("消息发送失败：" + data));
        return data;
    }
}
