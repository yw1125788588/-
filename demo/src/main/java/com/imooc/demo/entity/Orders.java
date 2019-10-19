package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Orders implements Serializable {
    private String id;

    private String pids;

    private Integer buynums;

    private Double total;

    private Integer state;

    private String address;

    private String name;

    private String telephone;

    private String uid;

    private Date createTime;

    private Date updateTime;
}