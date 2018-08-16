package com.rbs.cn.rest.biz.web;

import com.rbs.cn.rest.biz.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by admin on 2018/1/25.
 */
@RestController
public class DemoController {

    @Autowired
    DemoService demoService;

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
}
