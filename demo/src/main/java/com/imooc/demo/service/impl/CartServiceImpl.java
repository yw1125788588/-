package com.imooc.demo.service.impl;



import com.imooc.demo.dao.CartMapper;
import com.imooc.demo.dao.ProductMapper;
import com.imooc.demo.entity.*;
import com.imooc.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;


    /**
     * 1.加入购物车
     */
    @Override
    public Result addProductToCart(String uid, String pid, Integer number) {
        Result result = null;
        if (StringUtils.isEmpty(pid) || number == null || number <= 0) {
            result = new Result(false, "参数错误");
            return result;
        }
        //判断购物车中是否存在该商品了
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        //根据pid获得单价
        Product product = productMapper.selectByPrimaryKey(pid);
        if (product != null && !StringUtils.isEmpty(product.getId())) {
            if (carts.size() == 0) {
                //1.购物车中不存在该商品
                Cart cart = new Cart();
                cart.setId(UUID.randomUUID().toString());
                cart.setPid(pid);
                cart.setBuynum(number);
                cart.setSubtotal(product.getPrice() * number);
                cart.setUid(uid);
                cart.setCreateTime(new Date());
                cart.setUpdateTime(new Date());
                cartMapper.insert(cart);
                result = new Result(true, "添加成功", cart);
            } else {
                //1.购物车中存在该商品
                carts.get(0).setBuynum(carts.get(0).getBuynum() + number);
                carts.get(0).setSubtotal(carts.get(0).getSubtotal() + product.getPrice() * number);
                carts.get(0).setUpdateTime(new Date());
                cartMapper.updateByPrimaryKeySelective(carts.get(0));
                result = new Result(true, "添加成功", carts.get(0));
            }
        }
        return result;
    }

    /**
     * 根据uid查询所有并显示
     */
    @Override
    public List<QueryCart> findCartByUid(String uid) {
        List<QueryCart> queryCarts = new ArrayList<>();
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUidEqualTo(uid);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        if (carts != null && carts.size() != 0) {
            for (int i = 0; i < carts.size(); i++) {
                Product pro = productMapper.selectByPrimaryKey(carts.get(i).getPid());
                if (pro != null) {
                    QueryCart qc = new QueryCart();
                    qc.setPro(pro);
                    qc.setCart(carts.get(i));
                    queryCarts.add(qc);
                }
            }
        }
        return queryCarts;
    }

    /**
     * 删除多个商品(包含一键清空购物车 ,传入购物车的id)
     */
    @Override
    public Result deleteSomeCart(String[] ids) {
        for (String id : ids) {
            cartMapper.deleteByPrimaryKey(id);
        }
        return new Result(true, "删除成功");
    }

    @Override
    /** 增加某个商品的数量 */
    public Result addCartNum(String cid) {
        Result result = null;
        Cart cart = cartMapper.selectByPrimaryKey(cid);
        if (cart != null) {
            double price = cart.getSubtotal() / cart.getBuynum();
            cart.setBuynum(cart.getBuynum() + 1);
            cart.setSubtotal(cart.getSubtotal() + price);
            cartMapper.updateByPrimaryKeySelective(cart);
            result = new Result(true, "添加成功", cart);
        } else {
            result = new Result(false, "参数错误");
        }
        return result;
    }

    @Override
    /** 减少某个商品的数量 */
    public Result reduceCartNum(String cid) {
        Result result = null;
        Cart cart = cartMapper.selectByPrimaryKey(cid);
        if (cart != null) {
            //判断是否数量为1
            if (cart.getBuynum() == 1) {
                //数量为1 减一及删除购物项
                cartMapper.deleteByPrimaryKey(cid);
            } else {
                double price = cart.getSubtotal() / cart.getBuynum();
                cart.setBuynum(cart.getBuynum() - 1);
                cart.setSubtotal(cart.getSubtotal() - price);
                cartMapper.updateByPrimaryKeySelective(cart);
            }
            result = new Result(true, "减少成功", cart);
        } else {
            result = new Result(false, "参数错误");
        }
        return result;
    }

    @Override
    public List<QueryCart> submitCartToOrder(String[] ids) {
        List<QueryCart> carts = new ArrayList<>();
        //判断参数是否为空
        if (ids == null || ids.length == 0) {
            return null;
        }
        for (int i = 0; i < ids.length; i++) {
            Cart cart = cartMapper.selectByPrimaryKey(ids[i]);
            //判断id查询的cart是否存在
            if (cart != null) {
                Product pro = productMapper.selectByPrimaryKey(cart.getPid());
                if (pro != null) {
                    QueryCart qs = new QueryCart();
                    qs.setPro(pro);
                    qs.setCart(cart);
                    carts.add(qs);
                }
            }
        }
        return carts;
    }
}
