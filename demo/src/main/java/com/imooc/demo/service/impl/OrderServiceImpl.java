package com.imooc.demo.service.impl;

import com.imooc.demo.dao.CartMapper;
import com.imooc.demo.dao.OrdersMapper;
import com.imooc.demo.dao.ProductMapper;
import com.imooc.demo.entity.*;
import com.imooc.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;

    /**
     * 1.单个或多个商品下订单（先修改商品信息（数量和修改时间）再将订单保存数据库，并修改订单状态）
     */
    @Override
    public Result placeOrder(QueryVo vo) {
        Result result = null;
        if (vo.getCarts() == null || StringUtils.isEmpty(vo.getAddress()) || StringUtils.isEmpty(vo.getName())
                || StringUtils.isEmpty(vo.getTelephone()) || StringUtils.isEmpty(vo.getUid())) {
            result = new Result(false, "参数错误");
        } else {
            for (Cart cart : vo.getCarts()) {
                //1.修改商品信息
                Product product = productMapper.selectByPrimaryKey(cart.getPid());
                if (product != null && !StringUtils.isEmpty(product.getId())) {
                    //a.库存不足  购买失败
                    if (product.getNumber() < cart.getBuynum()) {
                        result = new Result(false, "库存不足，购买失败");
                        return result;
                    }
                    //b.刚好购买完
                    if (product.getNumber() == cart.getBuynum()) {
                        product.setNumber(0);
                        product.setStatus(0);
                        product.setUpdateTime(new Date());

                    }
                    //c.库存充足
                    if (product.getNumber() > cart.getBuynum()) {
                        product.setNumber(product.getNumber() - cart.getBuynum());
                        product.setUpdateTime(new Date());
                    }
                }
                //2.添加订单
                Orders orders = new Orders();
                orders.setId(UUID.randomUUID().toString());
                orders.setPids(cart.getPid());
                orders.setBuynums(cart.getBuynum());
                orders.setTotal(product.getPrice() * orders.getBuynums());
                orders.setState(1);
                orders.setAddress(vo.getAddress());
                orders.setTelephone(vo.getTelephone());
                orders.setName(vo.getName());
                orders.setUid(vo.getUid());
                orders.setCreateTime(new Date());
                orders.setUpdateTime(new Date());
                if (vo.getTotal() != null) {
                    vo.setTotal(vo.getTotal() + orders.getTotal());
                } else {
                    vo.setTotal(orders.getTotal());
                }
                //修改商品信息
                productMapper.updateByPrimaryKeySelective(product);
                //新增订单信息
                ordersMapper.insertSelective(orders);
                //3.判断是否需要删除购物车
                CartExample cartExample = new CartExample();
                cartExample.createCriteria().andUidEqualTo(orders.getUid()).andPidEqualTo(orders.getPids());
                List<Cart> carts = cartMapper.selectByExample(cartExample);
                //因为在加入购物车时加了判断  所以查询结果只会出现一个，所以不需要for循环遍历
                if (carts != null) {
                    cartMapper.deleteByPrimaryKey(carts.get(0).getId());
                }
            }
            result = new Result(true, "下单成功", vo);
        }
        return result;
    }

    /**
     * 2.查询所有订单
     */
    @Override
    public List<Orders> findAllOrders(String uid) {
        List<Orders> orders = null;
        if (!StringUtils.isEmpty(uid)) {
            OrdersExample ordersExample = new OrdersExample();
            ordersExample.createCriteria().andUidEqualTo(uid).andStateEqualTo(1);
            orders = ordersMapper.selectByExample(ordersExample);
        }
        return orders;
    }

    /**
     * 3.根据id查询订单（用于查看该订单详情）
     */
    @Override
    public Orders findOrderById(String oid) {
        Orders orders = null;
        if (!StringUtils.isEmpty(oid)) {
            orders = ordersMapper.selectByPrimaryKey(oid);
        }
        return orders;
    }

    /**
     * 4.删除订单
     */
    @Override
    public Result deleteOrder(String[] oid) {
        Result result = null;
        if (oid != null) {
            for (String id : oid) {
                ordersMapper.deleteByPrimaryKey(id);
            }
            result = new Result(true, "删除成功");
        } else {
            result = new Result(false, "参数错误");
        }
        return result;
    }

    /**
     * 5.修改订单（只能修改 订单的部分信息)
     */
    @Override
    public Result updataOrderById(String oid, String address, String name, String telephone) {
        Orders orders = ordersMapper.selectByPrimaryKey(oid);
        orders.setAddress(address);
        orders.setName(name);
        orders.setTelephone(telephone);
        ordersMapper.updateByPrimaryKeySelective(orders);
        Result result = new Result(true, "修改成功", orders);
        return result;
    }
}
