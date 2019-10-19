package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Product implements Serializable {
    private String id;

    private String name;

    private String image;

    private Integer isHot;

    private Integer number;

    private String besc;

    private Double price;

    private Integer status;

    private Boolean selected;

    private Integer category;

    private String bUid;

    private String lUid;

    private Date createTime;

    private Date updateTime;

}