/*
 * File: BasicRedisService.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-24
 */

package com.rbs.cn.rest.utils.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;


/**
 * Redis缓存服务的基础处理类，用于实现基础的Redis缓存处理
 * @param <T> 要存储的Value类型
 */
public class BasicRedisService<T> {
    static Logger logger = LoggerFactory.getLogger(BasicRedisService.class);

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 保存Value值
     * @param key 要保存的数据的key
     * @param value 要保存的数据的值
     */
    public void setValue(String key, T value){
        redisTemplate.boundValueOps(key).set(value);
    }

    /**
     * 获取数据
     * @param key 关键字
     * @return 保存的对象
     */
    public T getValue(String key){
        return (T)redisTemplate.boundValueOps(key).get();
    }


    /**
     * 删除缓存数据
     * @param key 关键字
     */
    public void removeValue(String key){
        redisTemplate.delete(key);
    }


    /**
     * 设置缓存的map值
     * @param key 缓存数据的key
     * @param mapKey 缓存的map的key
     * @param value 要缓存的值
     */
    public void setMapValue(String key, String mapKey, T value){
        redisTemplate.boundHashOps(key).put(mapKey, value);
    }

    /**
     * 获取缓存的Map的Hash值
     * @param key 缓存数据的key
     * @param mapKey 缓存的map的key
     * @return 缓存的数值
     */
    public T getMapValue(String key, String mapKey){
        return (T)redisTemplate.boundHashOps(key).get(mapKey);
    }

    /**
     * 删除缓存的Map中的某个值
     * @param key 缓存数据的key
     * @param mapKey 缓存的map中的key（要删除的key）
     */
    public void removeMapValue(String key, String mapKey){
        redisTemplate.boundHashOps(key).delete(mapKey);
        if(redisTemplate.boundHashOps(key).keys().size() == 0){
            redisTemplate.delete(key);
        }
    }

    /**
     * 获取缓存的Map中的所有mapKey值
     * @param key 缓存数据的key
     * @return 缓存的Map中的所有mapKey
     */
    public Set<String> getMapKeySet(String key){
        return redisTemplate.boundHashOps(key).keys();
    }

    /**
     * 添加数据到某个Set类型的缓存中
     * @param key 缓存数据的key
     * @param value 要缓存的数据
     */
    public void addSetValue(String key, T value){
        redisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 判断缓存中是否有相应的数据
     * @param key 缓存数据的key
     * @param value 要判断的value
     */
    public void hasSetValue(String key, T value){
        redisTemplate.boundSetOps(key).isMember(value);
    }

    /**
     * 删除缓存中Set中的数据
     * @param key 对应的key
     * @param value 要删除的数据
     */
    public void removeSetValue(String key, T value){
        redisTemplate.boundSetOps(key).remove(value);
        if(redisTemplate.boundSetOps(key).size() == 0){
            redisTemplate.delete(key);
        }
    }

    /**
     * 获取所有的Set缓存数据
     * @param key 缓存的key
     * @return 缓存的所有set
     */
    public Set<T> getAllSetValues(String key){
        return redisTemplate.boundSetOps(key).members();
    }
}
