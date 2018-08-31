/*
 * File: KafkaMessageHandlerImpl.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-28
 */

package com.rbs.cn.rest.utils.kafka.handle.impl;

import com.rbs.cn.rest.utils.kafka.handle.KafkaMessageHandler;
import com.rbs.cn.tools.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author fengtao.xue
 */
@Service("kafkaMessageHandlerImpl")
public class KafkaMessageHandlerImpl implements KafkaMessageHandler {
    static Logger logger = LoggerFactory.getLogger(KafkaMessageHandlerImpl.class);

    /*@Resource
    KafkaCommonService kafkaCommonService;*/

    @Override
    public void handle(String topic, String businessData) throws Exception {
        logger.info("kafka handler处理类接收消息："+ JSONUtils.toJson(businessData));

        /*KafkaMessage message = com.alibaba.fastjson.JSONObject.parseObject(businessData, KafkaMessage.class);
        KafkaMessageHeader header = message.getMessageHeader();
        String messageBody = message.getJsonString();

        logger.info("---开始处理MessageType为【{}】的消息....", header.getMessageType());

        switch (Enum.valueOf(MessageType.class, header.getMessageType())) {

            case LOG_SETTLE:
                kafkaCommonService.handleSettleLog(messageBody);
                break;

            default:
                break;
        }*/
    }
}
