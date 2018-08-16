/*
 * File: ProductEntity.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-08-10
 */

package com.rbs.cn.rest.biz.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class ProductEntity {
    static Logger logger = LoggerFactory.getLogger(ProductEntity.class);

    private int product;

    public ProductEntity(int product) {
        this.product = product;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }
}
