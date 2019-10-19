package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Sell implements Serializable {
    private String id;

    private String pid;

    private String uid;

    private Date createTime;

    private Date updateTime;

}