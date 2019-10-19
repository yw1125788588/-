package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private String id;

    private String username;

    private String password;

    private String name;

    private String telephone;

    private String address;

    private Integer sex;

    private Date createTime;

    private Date updateTime;

    private String headPortrait;

}