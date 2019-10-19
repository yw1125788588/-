package com.imooc.demo.service;

import com.imooc.demo.entity.Orders;
import com.imooc.demo.entity.QueryVo;
import com.imooc.demo.entity.Result;

import java.util.List;

public interface OrdersService {

    /** 1.单个或多个商品下订单（将订单保存数据库，并修改订单状态） */
    Result placeOrder(QueryVo vo);
    /** 2.查询所有订单 */
    List<Orders> findAllOrders(String uid);
    /** 3.根据id查询订单（用于查看该订单详情） */
    Orders findOrderById(String oid);
    /** 4.删除订单（修改订单状态） */
    Result deleteOrder(String[]  oid);
    /** 5.修改订单（只能修改 订单的部分信息) */
    Result updataOrderById(String oid, String address, String name, String telephone);
}
