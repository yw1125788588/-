package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {
    private String cid;

    private String name;
}