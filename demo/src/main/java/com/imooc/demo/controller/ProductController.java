package com.imooc.demo.controller;

import com.imooc.demo.entity.PageResult;
import com.imooc.demo.entity.Product;
import com.imooc.demo.entity.Result;
import com.imooc.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


//    /** 1.发布商品 */
//    @RequestMapping("/addProduct.do")
//    @ResponseBody
//    public Result addProduct(Product pro , HttpServletRequest request) {
//        Result result = null;
//        try{
//            User user = (User)request.getSession().getAttribute("user");
//            if (user == null || StringUtils.isEmpty(user.getId())){
//                result = new Result(false,"请先登录");
//                return result;
//            }
//            pro.setbUid(user.getId());
//            result = productService.addProduct(pro);
//        }catch (Exception e){
//            result = new Result(false,e.getMessage());
//        }
//        return result;
//    }

    /**
     * 2.查询指定商品详情 （根据书的id）
     */
    @RequestMapping("/findProductById.do")
    @ResponseBody
    public Product findProductById(String id) {
        Product pro = null;
        try {
            pro = productService.findProductById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pro;
    }

    /**
     * 3.分页显示商品（类别查询）
     */
    @RequestMapping("/findProductPage.do")
    @ResponseBody
    public PageResult findProductPage(
            @RequestBody Product pro,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "9") int pageSize) {
        PageResult pageResult = null;
        try {
            pageResult = productService.findProductPage(pro, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageResult;
    }

    /**
     * 4.分页显示（模糊查询）
     */
    @RequestMapping("/findLikeProductPage.do")
    @ResponseBody
    public PageResult findLikeProductPage(
            @RequestBody Product pro,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "9") int pageSize) {
        PageResult pageResult = null;
        try {
            pageResult = productService.findLikeProductPage(pro, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageResult;
    }

    /**
     * 5.查询热门商品（用于首页展示，后台定死查询数量，不需要分页）
     */
    @RequestMapping("/findHotProducts.do")
    @ResponseBody
    public List<Product> findHotProducts() {
        List<Product> hotProducts = null;
        try {
            hotProducts = productService.findHotProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotProducts;
    }

    /**
     * 6.查询最新商品（用于首页展示，后台定死查询数量，不需要分页）
     */
    @RequestMapping("/findLatestProducts.do")
    @ResponseBody
    public List<Product> findLatestProducts() {
        List<Product> latestProducts = null;
        try {
            latestProducts = productService.findLatestProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latestProducts;
    }

    /**
     * 7.购买商品（修改商品的数量，商品数减number）
     */
    @RequestMapping("/updateProduct.do")
    @ResponseBody
    public Result updateProduct(String pid, Integer number) {
        Result result = null;
        try {
            result = productService.updateProduct(pid, number);
        } catch (Exception e) {
            result = new Result(false, e.getMessage());
        }
        return result;
    }

    /**
     * 8查询商品的数量（用于前台的校验,可实现最大购买量不超过库存。。。。待定）
     */
    @RequestMapping("/findNumberById.do")
    @ResponseBody
    public Integer findNumberById(String pid) {
        Integer number = 0;
        try {
            number = productService.findNumberById(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }
}
