package com.imooc.demo.controller;

import com.imooc.demo.entity.Product;
import com.imooc.demo.entity.Result;
import com.imooc.demo.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sell")
public class SellController {

    @Autowired
    private SellService sellService;

    /**
     * 1.发布商品 （已发布未售出）
     */
    @RequestMapping("/addproductToSell.do")
    @ResponseBody
    public Result addproductToSell(String uid, @RequestBody Product pro) {
        Result result = null;
        try {
            result = sellService.addproductToSell(uid, pro);
        } catch (Exception e) {
            result = new Result(false, e.getMessage());
        }
        return result;
    }

    /**
     * 2.显示发布未售出的商品()
     */
    @RequestMapping("/findSellProduct.do")
    @ResponseBody
    public List<Product> findSellProduct(String lid) {
        List<Product> products = null;
        try {
            products = sellService.findSellProduct(lid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * 3.显示发布售出的商品()
     */
    @RequestMapping("/findSelledProduct.do")
    @ResponseBody
    public List<Product> findSelledProduct(String lid) {
        List<Product> products = null;
        try {
            products = sellService.findSelledProduct(lid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * 4删除商品 （未出售，用户不想再出售的商品） //删除两次，一次删除product中的数据，一次删除出售类中的数据
     */
    @RequestMapping("/deleteProductToSell.do")
    @ResponseBody
    public Result deleteProductToSell(String uid, String pid) {
        Result result = null;
        try {
            result = sellService.deleteProductToSell(uid, pid);
        } catch (Exception e) {
            result = new Result(false, e.getMessage());
        }
        return result;
    }
}
