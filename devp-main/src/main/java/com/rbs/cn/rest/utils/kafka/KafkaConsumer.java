/*
 * File: KafkaConsumer.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-24
 */

package com.rbs.cn.rest.utils.kafka;

import com.rbs.cn.rest.biz.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * @author fengtao.xue
 */
@Component
public class KafkaConsumer {
    static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    /**
     * 监听test主题,有消息就读取
     * @param message
     */
    @KafkaListener(topics = {"topic-test"})
    public void consumer(User message){
        logger.info("test topic message : {}", message);
    }

}
