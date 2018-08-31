/*
 * File: KafkaMessageListener.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-28
 */

package com.rbs.cn.rest.utils.kafka.listener;

import com.rbs.cn.rest.utils.kafka.handle.KafkaMessageHandler;
import com.rbs.cn.tools.json.JSONUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author fengtao.xue
 */
public class KafkaMessageListener implements MessageListener<String, String> {
    static Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    private KafkaMessageHandler kafkaMessageHandler;

    /**
     * 默认线程池大小 设置为1是为了调试方便；实际使用时应该配置为池的优化大小
     */
    private static Integer CORE_THREAD_NUM = 10;

    /**
     * 定义线程处理器的线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(getDefaultThreadNum());

    public KafkaMessageListener(KafkaMessageHandler kafkaMessageHandler){
        this.kafkaMessageHandler = kafkaMessageHandler;
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        logger.info("【kafka消息接收 开始：" + "topic="+data.topic()+",partition="+data.partition()+",offset="+data.offset()+"】");
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                String businessData = data.value();
                try {
                    kafkaMessageHandler.handle(data.topic(), businessData);
                } catch (Exception e) {
                    logger.error("kafka消息处理失败：" + JSONUtils.toJson(data.value()),e);
                }
                logger.info("【kafka消息接收 结束：" + "topic="+data.topic()+",partition="+data.partition()+",offset="+data.offset()+"】");
            }
        });
    }

    /**
     * 默认配置用与 cpu核数 相同个数的线程
     *
     * @return
     */
    protected int getDefaultThreadNum() {
        int cpuNum = Runtime.getRuntime().availableProcessors();
        if(cpuNum > 0) {
            return cpuNum;
        } else {
            return CORE_THREAD_NUM;
        }
    }
}
