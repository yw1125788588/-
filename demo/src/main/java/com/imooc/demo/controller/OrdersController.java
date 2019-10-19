package com.imooc.demo.controller;

import com.imooc.demo.entity.Orders;
import com.imooc.demo.entity.QueryVo;
import com.imooc.demo.entity.Result;
import com.imooc.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /** 1.单个或多个商品下订单（将订单保存数据库，并修改订单状态） */
    @RequestMapping("/placeOrder.do")
    @ResponseBody
    public Result placeOrder(@RequestBody QueryVo vo) {
        Result result = null;
        try {
            result = ordersService.placeOrder(vo);
        } catch (Exception e) {
            result = new Result(false,e.getMessage());
        }
        return result;
    }

    /** 2.查询所有订单 */
    @RequestMapping("/findAllOrders.do")
    @ResponseBody
    public List<Orders> findAllOrders(String uid) {
        List<Orders> orders = null;
        try {
            orders = ordersService.findAllOrders(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /** 3.根据id查询订单（用于查看该订单详情） */
    @RequestMapping("/findOrderById.do")
    @ResponseBody
    public Orders findOrderById(String oid) {
        Orders orders = null;
        try {
            orders = ordersService.findOrderById(oid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /** 4.删除订单（修改订单状态） */
    @RequestMapping("/deleteOrder.do")
    @ResponseBody
    public Result deleteOrder(String[] oid) {
        Result result = null;
        try {
            result = ordersService.deleteOrder(oid);
        } catch (Exception e) {
            result = new Result(false,e.getMessage());
        }
        return result;
    }

    /** 5.修改订单（只能修改 订单的部分信息) */
    @RequestMapping("/updataOrderById.do")
    @ResponseBody
    public Result updataOrderById(String oid, String address, String name, String telephone) {
        Result result = null;
        try {
            result = ordersService.updataOrderById(oid,address,name,telephone);
        } catch (Exception e) {
            result = new Result(false,e.getMessage());
        }
        return result;
    }
}
