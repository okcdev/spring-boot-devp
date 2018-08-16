package com.rbs.cn.rest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by fengtao.xue on 2018/1/16.
 */

@SpringBootApplication
//@MapperScan(basePackages = {"com.rbs.cn.rest"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
