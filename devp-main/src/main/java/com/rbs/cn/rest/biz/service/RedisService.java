/*
 * File: DemoRedisService.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-24
 */

package com.rbs.cn.rest.biz.service;

import com.rbs.cn.rest.biz.entity.User;
import com.rbs.cn.rest.utils.redis.service.BasicRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 对Vdata通过Redis进行缓存的读写类，通过继承BasicRedisService实现以下功能：<br />
 * <ul>
 *     <li>1. 缓存数据（setValue方法）</li>
 *     <li>2. 获取缓存数据（getValue方法）</li>
 *     <li>3. 删除数据（removeValue方法）</li>
 *     <li>4. 缓存Map数据（setMapValue方法）</li>
 *     <li>5. 获取Map数据（getMapValue方法）</li>
 *     <li>6. 删除Map数据（removeMapValue方法）</li>
 *     <li>7. 添加Set数据</li>
 *     <li>8. 判断Set中是否存在此数据</li>
 *     <li>9. 获取所有的Set数据</li>
 * </ul>
 */
@Service
public class RedisService extends BasicRedisService<User> {
    static Logger logger = LoggerFactory.getLogger(RedisService.class);
}
