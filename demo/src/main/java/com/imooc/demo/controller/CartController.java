package com.imooc.demo.controller;


import com.imooc.demo.entity.QueryCart;
import com.imooc.demo.entity.Result;
import com.imooc.demo.entity.User;
import com.imooc.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    /** 1.加入购物车     保存商品信息和数量到数据库   商品的信息不变*/
    @RequestMapping("/addProductToCart.do")
    @ResponseBody
    public Result addProductToCart(String uid, String pid, Integer number, HttpServletRequest request) {
        Result result = null;
        try{
            // 获得上传书籍的用户id
            User user = (User) request.getSession().getAttribute("user");
            System.out.println(user.toString());
            if (user == null || StringUtils.isEmpty(user.getId())) {
                result = new Result(false, "请先登录");
                return result;
            }
            result = cartService.addProductToCart(uid, pid, number);
        }catch (Exception e){
            result = new Result(false,e.getMessage());
        }
        return result;
    }

    /** 2.根据uid查询所有并显示 */
    @RequestMapping("/findCartByUid.do")
    @ResponseBody
    public List<QueryCart> findCartByUid(String uid) {
        List<QueryCart> queryCarts = null;
        try{
            queryCarts = cartService.findCartByUid(uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return queryCarts;
    }

    /** 3.删除多个商品(包含一键清空购物车 ,传入购物车的id) */
    @RequestMapping("/deleteSomeCart.do")
    @ResponseBody
    public Result deleteSomeCart(String[] ids) {
        Result result = null;
        try{
            result = cartService.deleteSomeCart(ids);
        }catch (Exception e){
            result = new Result(false,e.getMessage());
        }
        return result;
    }

    /** 增加某个商品的数量 */
    @RequestMapping("/addCartNum.do")
    @ResponseBody
    public Result addCartNum(String cid) {
        Result result = null;
        try{
            result = cartService.addCartNum(cid);
        }catch (Exception e){
            result = new Result(false,e.getMessage());
        }
        return result;
    }

    /** 减少某个商品的数量 */
    @RequestMapping("/reduceCartNum.do")
    @ResponseBody
    public Result reduceCartNum(String cid) {
        Result result = null;
        try{
            result = cartService.reduceCartNum(cid);
        }catch (Exception e){
            result = new Result(false,e.getMessage());
        }
        return result;
    }

    /** 提交一个或多个购物项到订单页面 */
    @RequestMapping("/submitCartToOrder.do")
    @ResponseBody
    public List<QueryCart> submitCartToOrder(String[] ids) {
        List<QueryCart> carts = null;
        try{
            carts = cartService.submitCartToOrder(ids);
        }catch (Exception e){
            e.printStackTrace();
        }
        return carts;
    }
//    /** 提交一个或多个购物项（在购物车中提交，购物项的id） */
//    @RequestMapping("/submitOrderByCart.do")
//    @ResponseBody
//    public Result submitOrderByCart(String[] ids) {
//        Result result = null;
//        try{
//            result = cartService.submitOrderByCart(ids);
//            //当订单提交成功后删除购物车中的数据
//            cartService.deleteSomeCart(ids);
//        }catch (Exception e){
//            result = new Result(false,e.getMessage());
//        }
//        return result;
//    }
}


