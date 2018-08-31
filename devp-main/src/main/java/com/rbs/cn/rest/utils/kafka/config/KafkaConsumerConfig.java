/*
 * File: KafkaConsumerConfig.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-28
 */

package com.rbs.cn.rest.utils.kafka.config;

import com.rbs.cn.rest.utils.kafka.handle.KafkaMessageHandler;
import com.rbs.cn.rest.utils.kafka.listener.KafkaMessageListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;
import java.util.Map;


/**
 * @author fengtao.xue
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    static Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Value("${bootstrap.servers}")
    private String servers;

    @Value("${kafka.topics}")
    private String topics;

    ConsumerFactory<String, String> consumerFactory;

    //基础属性配置
    private Map<String, Object> consumerConfigs(){
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        //组ID
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "acs_settle_consumer");
        /**
         * 如果设置成true,偏移量由auto.commit.interval.ms控制自动提交的频率。
         *
         * 如果设置成false,不需要定时的提交offset，可以自己控制offset，当消息认为已消费过了，这个时候再去提交它们的偏移量。
         * 这个很有用的，当消费的消息结合了一些处理逻辑，这个消息就不应该认为是已经消费的，直到它完成了整个处理。
         */
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        //提交延迟毫秒数
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        //执行超时时间
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        /**
         * 在consumter端配置文件中(或者是ConsumerConfig类参数)有个"autooffset.reset"(在kafka 0.8版本中为auto.offset.reset),
         * 有2个合法的值"largest"/"smallest",默认为"largest",此配置参数表示当此groupId下的消费者,在ZK中没有offset值时(比如新的groupId,或者是zk数据被清空),
         * consumer应该从哪个offset开始消费.largest表示接受接收最大的offset(即最新消息),smallest表示最小offset,即从topic的开始位置消费所有消息.
         */
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return propsMap;
    }

    //消息监听配置
    @Bean
    @Qualifier("kafkaMessageListener")
    public KafkaMessageListener kafkaMessageListener(
            @Qualifier("kafkaMessageHandlerImpl") KafkaMessageHandler handler) {
        return new KafkaMessageListener(handler);
    }

    public ConsumerFactory<String, String> consumerFactory() {
        if(consumerFactory == null){
            consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerConfigs());
        }
        return consumerFactory;
    }

    //配置容器属性，包含消息监听器和监听的topics
    @Bean
    @Qualifier("containerProperties")
    ContainerProperties containerProperties(
            @Qualifier("kafkaMessageListener") KafkaMessageListener kafkaMessageListener) {
        ContainerProperties containerProperties = new ContainerProperties(topics.split(","));
        containerProperties.setMessageListener(kafkaMessageListener);
        return containerProperties;
    }

    @Bean
    KafkaMessageListenerContainer<String, String> messageListenerContainer(
            @Qualifier("containerProperties") ContainerProperties containerProperties) {
        return new KafkaMessageListenerContainer<String, String>(consumerFactory(), containerProperties);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
}
