package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryVo implements Serializable {

    private List<Cart> carts;

    private Double total;

    private String address;

    private String name;

    private String telephone;

    private String uid;

}
