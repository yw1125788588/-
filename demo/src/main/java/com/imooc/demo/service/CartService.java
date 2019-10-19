package com.imooc.demo.service;

import com.imooc.demo.entity.QueryCart;
import com.imooc.demo.entity.Result;

import java.util.List;

public interface CartService {

    /**
     * 1.加入购物车
     */
    Result addProductToCart(String uid, String pid, Integer number);

    /**
     * 根据uid查询所有并显示
     */
    List<QueryCart> findCartByUid(String uid);

    /**
     * 删除多个商品(包含一键清空购物车 ,传入购物车的id)
     */
    Result deleteSomeCart(String[] ids);

    /**
     * 增加某个商品的数量
     */
    Result addCartNum(String cid);

    /**
     * 减少某个商品的数量
     */
    Result reduceCartNum(String cid);

    /**
     * 提交一个或多个购物项到订单页面
     */
    List<QueryCart> submitCartToOrder(String[] ids);
}
