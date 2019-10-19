package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Cart implements Serializable {
    private String id;

    private String pid;

    private Integer buynum;

    private Double subtotal;

    private String uid;

    private Date createTime;

    private Date updateTime;

    
}