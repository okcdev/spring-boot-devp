/*
 * File: KafkaSendUtil.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-28
 */

package com.rbs.cn.rest.utils.kafka.sender;

import com.rbs.cn.rest.utils.SpringContextUtil;
import com.rbs.cn.tools.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;


/**
 * @author fengtao.xue
 */
public class KafkaSendUtil {
    static Logger logger = LoggerFactory.getLogger(KafkaSendUtil.class);

    //指定 topic 和 message
    public static void send(String topic, String obj) {
        logger.info("kafka Producer发送消息，topic="+topic);
        KafkaTemplate<String, String> kafkaTemplate = (KafkaTemplate<String, String>) SpringContextUtil.getBean("kafkaTemplate");
        ListenableFuture<SendResult<String, String>> listenableFuture= kafkaTemplate.send(topic,obj);
        try {
            SendResult<String,String> sendResult = listenableFuture.get();
            listenableFuture.addCallback(SuccessCallback -> {
                        logger.info("kafka Producer发送消息成功！topic=" + sendResult.getRecordMetadata().topic()+",partition"+sendResult.getRecordMetadata().partition()+",offset="+sendResult.getRecordMetadata().offset());
                    },
                    FailureCallback->logger.error("kafka Producer发送消息失败！sendResult=" + JSONUtils.toJson(sendResult.getProducerRecord())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //指定 topic、分区 和 message
    public static void send(String topic, int partition, String data){
        logger.info("kafka Producer发送消息，topic="+topic);
        KafkaTemplate<String, String> kafkaTemplate = (KafkaTemplate<String, String>) SpringContextUtil.getBean("kafkaTemplate");
        ListenableFuture<SendResult<String, String>> listenableFuture= kafkaTemplate.send(topic,partition, data);

        try {
            SendResult<String,String> sendResult = listenableFuture.get();
            listenableFuture.addCallback(SuccessCallback -> {
                        logger.info("kafka Producer发送消息成功！topic=" + sendResult.getRecordMetadata().topic()+",partition"+sendResult.getRecordMetadata().partition()+",offset="+sendResult.getRecordMetadata().offset());
                    },
                    FailureCallback->logger.error("kafka Producer发送消息失败！sendResult=" + JSONUtils.toJson(sendResult.getProducerRecord())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
