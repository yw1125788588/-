package com.imooc.demo.service.impl;

import com.imooc.demo.dao.ProductMapper;
import com.imooc.demo.dao.SellMapper;
import com.imooc.demo.entity.*;
import com.imooc.demo.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SellServiceImpl implements SellService {

    @Autowired
    private SellMapper sellMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 1.发布商品 （已发布未借出）
     */
    @Override
    public Result addproductToSell(String uid, Product pro) {
        Result result = null;
        //1.将上传的商品添加到product表中
        if (pro == null || StringUtils.isEmpty(pro.getName()) ||
                StringUtils.isEmpty(pro.getImage()) ||
                pro.getNumber() == null || pro.getNumber() <= 0 ||
                StringUtils.isEmpty(pro.getBesc()) ||
                pro.getPrice() == null || pro.getPrice() <= 0 ||
                pro.getCategory() == null) {
            result = new Result(false, "请填写完整信息");
        } else {
            pro.setId(UUID.randomUUID().toString());
            pro.setIsHot(0);
            pro.setStatus(1);
            pro.setBUid(uid);
            pro.setImage("http://120.79.240.142:8080/images/" + pro.getImage());
            pro.setCreateTime(new Date());
            pro.setUpdateTime(new Date());
            productMapper.insertSelective(pro);
            //2.将商品信息和userid添加到sell表中
            Sell sell = new Sell();
            sell.setId(UUID.randomUUID().toString());
            sell.setPid(pro.getId());
            sell.setUid(uid);
            sell.setCreateTime(new Date());
            sell.setUpdateTime(new Date());
            sellMapper.insertSelective(sell);
            result = new Result(true, "添加成功", sell);
        }
        return result;
    }

    /**
     * 2.显示发布未售出的商品()
     */
    @Override
    public List<Product> findSellProduct(String lid) {
        List<Product> products = null;
        if (!StringUtils.isEmpty(lid)) {
            ProductExample productExample = new ProductExample();
            productExample.createCriteria().andBUidEqualTo(lid).andStatusEqualTo(1);
            products = productMapper.selectByExample(productExample);
        }
        return products;
    }

    /**
     * 3.显示发布售出的商品()
     */
    @Override
    public List<Product> findSelledProduct(String lid) {
        List<Product> products = null;
        if (!StringUtils.isEmpty(lid)) {
            ProductExample productExample = new ProductExample();
            productExample.createCriteria().andBUidEqualTo(lid).andStatusEqualTo(0);
            products = productMapper.selectByExample(productExample);
        }
        return products;
    }

    /**
     * 4删除商品 （未出售，用户不想再出售的商品） //删除两次，一次删除product中的数据，一次删除出售类中的数据
     */
    @Override
    public Result deleteProductToSell(String uid, String pid) {
        Result result = null;
        if (!StringUtils.isEmpty(uid) && !StringUtils.isEmpty(pid)) {
            //1.删除product中的数据(将数量改成0)
            Product product = productMapper.selectByPrimaryKey(pid);
            product.setNumber(0);
            product.setStatus(0);
            product.setUpdateTime(new Date());
            productMapper.updateByPrimaryKeySelective(product);
            //2.删除sell表格中的数据
            SellExample sellExample = new SellExample();
            sellExample.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid);
            sellMapper.deleteByExample(sellExample);
            result = new Result(true, "删除成功");
        }
        return result;
    }
}
