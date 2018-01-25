package com.rbs.cn.rest.biz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by admin on 2018/1/25.
 */
@Service("DemoService")
@Deprecated
public class DemoService {

    @Transactional(readOnly = false)
    public void call(){
        //todo
    }
}
