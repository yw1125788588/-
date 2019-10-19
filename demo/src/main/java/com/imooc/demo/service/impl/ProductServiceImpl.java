package com.imooc.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.demo.dao.ProductMapper;
import com.imooc.demo.entity.PageResult;
import com.imooc.demo.entity.Product;
import com.imooc.demo.entity.Result;
import com.imooc.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 2.查询指定商品详情 （根据书的id）
     */
    @Override
    public Product findProductById(String id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    /**
     * 3.分页显示商品（类别查询）
     */
    @Override
    public PageResult findProductPage(Product pro, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ProductExample tbe = null;
        if (pro != null) {
            tbe = new ProductExample();
            ProductExample.Criteria criteria = tbe.createCriteria();

            if (pro.getCategory() != null) {
                criteria.andCategoryEqualTo(pro.getCategory()).andNumberGreaterThan(0).andStatusEqualTo(1);
            }
            // 此处可以扩展
        }

        Page<Product> page = (Page<Product>) productMapper.selectByExample(tbe);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 4.分页显示（模糊查询）
     */
    @Override
    public PageResult findLikeProductPage(Product pro, int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//
//        ProductExample tbe = null;
//        if (pro != null) {
//            tbe = new ProductExample();
//            ProductExample.Criteria criteria = tbe.createCriteria();
//
//            if (pro.getName() != null) {
//                criteria.andNameLike("%" + pro.getName() + "%").andStatusEqualTo(1).andNumberGreaterThan(0);
//            }
//            // 此处可以扩展
//        }
//
//        Page<Product> page = (Page<Product>) productMapper.selectByExample(tbe);
//
//        return new PageResult(page.getTotal(), page.getResult());
        return null;
    }

    /**
     * 5.查询热门商品（用于首页展示，后台定死查询数量，不需要分页）
     */
    @Override
    public List<Product> findHotProducts() {
        List<Product> products = productMapper.selectHottestProducts();
        return products;
    }

    /**
     * 6.查询最新商品（用于首页展示，后台定死查询数量，不需要分页）
     */
    @Override
    public List<Product> findLatestProducts() {
        List<Product> products = productMapper.selectLatestProducts();
        return products;
    }

    /**
     * 7.购买商品（修改商品的数量，商品数减number）
     */
    @Override
    public Result updateProduct(String pid, Integer number) {
        Result result = null;
        Product product = productMapper.selectByPrimaryKey(pid);
        //根据id获得商品后判断购买数量是否超出范围
        if (product != null && !StringUtils.isEmpty(product.getId())) {
            //1.库存不足  购买失败
            if (product.getNumber() < number) {
                result = new Result(false, "库存不足，购买失败");
                return result;
            }
            //2.刚好购买完
            if (product.getNumber() == number) {
                product.setNumber(0);
                product.setStatus(0);
                product.setUpdateTime(new Date());
                productMapper.updateByPrimaryKeySelective(product);
                result = new Result(true, "购买成功", product);
            }
            if (product.getNumber() > number) {
                product.setNumber(product.getNumber() - number);
                product.setUpdateTime(new Date());
                productMapper.updateByPrimaryKeySelective(product);
                result = new Result(true, "购买成功", product);
            }
        }
        return result;
    }

    /**
     * 8查询商品的数量（用于前台的校验,可实现最大购买量不超过库存。。。。待定）
     */
    @Override
    public Integer findNumberById(String pid) {
        Product product = productMapper.selectByPrimaryKey(pid);
        if (product != null && !StringUtils.isEmpty(product.getId())) {
            return product.getNumber();
        }
        return null;
    }
}
