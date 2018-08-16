package com.rbs.cn.rest.biz.service;

import com.rbs.cn.rest.biz.entity.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2018/1/25.
 */
@Service("DemoService")
public class DemoService {
    static Logger logger = LoggerFactory.getLogger(DemoService.class);
    private static ProductEntity entity ;

    static int MAX_PRODUCT = 10;
    static int MIN_PRODUCT = 7;

    static {
        entity = new ProductEntity(8);
    }

    public void produce(){
        logger.debug("befor produce:{}", entity.getProduct());
        doProduce();
        logger.debug("after produce:{}", entity.getProduct());
    }

    public void consumer(){
        logger.debug("befor consumer:{}", entity.getProduct());
        doConsume();
        logger.debug("after consumer:{}", entity.getProduct());
    }

    public synchronized void doProduce()
    {
        if(entity.getProduct() >= MAX_PRODUCT)
        {
            try
            {
                wait();
                System.out.println("产品已满,请稍候再生产");
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            return;
        }

        entity.setProduct(entity.getProduct()+1);
        System.out.println("生产者生产第" + entity.getProduct() + "个产品.");
        notifyAll();   //通知等待区的消费者可以取出产品了
    }

    /**
     * 消费者从店员取产品
     */
    public synchronized void doConsume()
    {
        if(entity.getProduct() <= MIN_PRODUCT)
        {
            try
            {
                wait();
                System.out.println("缺货,稍候再取");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return;
        }

        System.out.println("消费者取走了第" + entity.getProduct() + "个产品.");
        entity.setProduct(entity.getProduct()-1);
        notifyAll();   //通知等待去的生产者可以生产产品了
    }

}
