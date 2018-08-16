/*
 * File: ModelService.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-16
 */

package com.rbs.cn.rest.biz.service;

import com.rbs.cn.rest.biz.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author fengtao.xue
 */
@Service
public class ModelService {
    static Logger logger = LoggerFactory.getLogger(ModelService.class);

    public static List<User> USER_LIST = new ArrayList<>();
    static {
        USER_LIST.add(new User("张三","男",23));
        USER_LIST.add(new User("李四","男",23));
        USER_LIST.add(new User("王五","女",23));
        USER_LIST.add(new User("赵六","男",23));
        USER_LIST.add(new User("朱七","男",23));
    }


    public List<User>getUserList(){
        return USER_LIST;
    }

    public List<User> addUser(User user){
        USER_LIST.add(user);
        return USER_LIST;
    }

    public List<User> delUser(String name){
        Iterator<User> iterator = USER_LIST.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if (name.equals(user.getName()))
                USER_LIST.remove(user);
        }
        return USER_LIST;
    }
}
