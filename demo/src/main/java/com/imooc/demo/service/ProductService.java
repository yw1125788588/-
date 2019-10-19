package com.imooc.demo.service;

import com.imooc.demo.entity.PageResult;
import com.imooc.demo.entity.Product;
import com.imooc.demo.entity.Result;

import java.util.List;

public interface ProductService {
    //    /** 1.发布商品 */
//    Result addProduct(Product pro);

    /** 2.查询指定商品详情 （根据书的id） */
    Product findProductById(String id);

    /** 3.分页显示商品（类别查询） */
    PageResult findProductPage(Product pro, int pageNum, int pageSize);

    /** 4.分页显示（模糊查询） */
    PageResult findLikeProductPage(Product pro, int pageNum, int pageSize);

    /** 5.查询热门商品（用于首页展示，后台定死查询数量，不需要分页） */
    List<Product> findHotProducts();

    /** 6.查询最新商品（用于首页展示，后台定死查询数量，不需要分页） */
    List<Product> findLatestProducts();

    /** 7.购买商品（修改商品的数量，商品数减number） */
    Result updateProduct(String pid, Integer number);

    /** 8查询商品的数量（用于前台的校验,可实现最大购买量不超过库存。。。。待定） */
    Integer findNumberById(String pid);
}
