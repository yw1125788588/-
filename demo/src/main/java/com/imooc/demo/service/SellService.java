package com.imooc.demo.service;

import com.imooc.demo.entity.Product;
import com.imooc.demo.entity.Result;

import java.util.List;

public interface SellService {

    /** 1.发布商品 （已发布未售出） */
    Result addproductToSell(String uid, Product pro);

    /** 2.显示发布未售出的商品() */
    List<Product> findSellProduct(String lid);

    /** 3.显示发布售出的商品() */
    List<Product> findSelledProduct(String lid);

    /** 4删除商品 （未出售，用户不想再出售的商品） //删除两次，一次删除product中的数据，一次删除出售类中的数据 */
    Result deleteProductToSell(String uid, String pid);
}
