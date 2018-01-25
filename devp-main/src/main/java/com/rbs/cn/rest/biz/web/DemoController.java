package com.rbs.cn.rest.biz.web;

import com.rbs.cn.rest.biz.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2018/1/25.
 */
@RestController
@Deprecated
public class DemoController {

    @Autowired
    DemoService demoService;

    @RequestMapping("/api/s2/v1")
    @Deprecated
    public void call(@RequestParam String id, @RequestBody String postJson){
        demoService.call();
    }
}
