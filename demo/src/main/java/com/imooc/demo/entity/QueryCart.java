package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于提交购物车到订单页面的信息
 */
@Data
public class QueryCart implements Serializable {

    private Cart cart;
    private Product pro;

}
