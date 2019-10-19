package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页显示
 */
@Data
public class PageResult implements Serializable {
    private long total; // 总记录数
    private List row; // 每页显示的记录

    public PageResult() {
    }

    public PageResult(long total, List row) {
        this.total = total;
        this.row = row;
    }

}
