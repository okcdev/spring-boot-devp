/*
 * File: User.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-16
 */

package com.rbs.cn.rest.biz.entity;

import com.rbs.cn.commons.entity.BasicEntity;
import com.rbs.cn.tools.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class User {
    static Logger logger = LoggerFactory.getLogger(User.class);

    private String name;
    private String gender;
    private Integer age;

    public User(String name) {
        this.name = name;
    }

    public User(){

    }

    public User(String name, String gender, Integer age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return JSONUtils.toJson(this);
    }
}
