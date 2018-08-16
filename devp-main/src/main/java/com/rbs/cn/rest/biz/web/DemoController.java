package com.rbs.cn.rest.biz.web;

import com.rbs.cn.rest.biz.entity.Result;
import com.rbs.cn.rest.biz.entity.User;
import com.rbs.cn.rest.biz.service.DemoService;
import com.rbs.cn.rest.biz.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/1/25.
 */
@RestController
public class DemoController {
    static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    DemoService demoService;

    @Autowired
    ModelService modelService;

    @RequestMapping("/produce")
    public String produce(){
        demoService.produce();
        return "success";
    }

    @RequestMapping("/consumer")
    public String consumer(){
        demoService.consumer();
        return "success";
    }

    @RequestMapping("/userList")
    public Result<List> userList(){
        return new Result<>(2000, "调用成功", modelService.getUserList());
    }

    @RequestMapping("/addUser")
    public Result<List> addUser(){
        User user = new User("乔杉","男",34);
        return new Result<>(2000, "调用成功", modelService.addUser(user));
    }

    @RequestMapping("/delUser/{name}")
    public Result<List> delUser(@PathVariable("name") String name){
        logger.debug("name:{}", name);
        return new Result<>(2000, "调用成功", modelService.delUser(name));
    }
}
