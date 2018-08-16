/*
 * File: Result.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-16
 */

package com.rbs.cn.rest.biz.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class Result<T> {
    static Logger logger = LoggerFactory.getLogger(Result.class);

    private static Integer STATUS_SUCCESS = 2000;
    private static Integer STATUS_ERROR = 4001;
    private static Integer STATUS_OTHER = 5001;

    private Integer status;
    private String message;
    private T data;

    public Result(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
