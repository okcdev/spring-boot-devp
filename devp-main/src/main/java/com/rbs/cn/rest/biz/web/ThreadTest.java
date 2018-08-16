/*
 * File: ThreadTest.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-10
 */

package com.rbs.cn.rest.biz.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


/**
 * @author fengtao.xue
 */
public class ThreadTest {
    static Logger logger = LoggerFactory.getLogger(ThreadTest.class);
    public static void main(String[] args) {
        /*MyThread T1 = new MyThread("A");
        MyThread T2 = new MyThread("B");
        MyThread T3 = new MyThread("C");
        T1.start();
        T2.start();
        T3.start();

        //测试Runnable
        MyThread1 t1 = new MyThread1();
        new Thread(t1).start();//同一个t1，如果在Thread中就不行，会报错
        new Thread(t1).start();
        new Thread(t1).start();*/

        HashMap hashMap = new HashMap();
        hashMap.put("a","ssss");
        logger.debug("hashMap.entrySet():{}", hashMap.entrySet());

    }

}

class MyThread extends Thread {
    private String name;
    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name+":"+i);
            try {
                sleep(1000); //休眠1秒，避免太快导致看不到同时执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class MyThread1 implements Runnable {
    private int ticket = 10;

    @Override
    //记得要资源公共，要在run方法之前加上synchronized关键字，要不然会出现抢资源的情况
    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            if (this.ticket > 0) {
                System.out.println("卖票：ticket" + this.ticket--);
            }
        }

    }
}