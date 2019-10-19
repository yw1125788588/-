package com.imooc.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

  private boolean success; // 状态

  private String message; // 消息体

  private Object data; // 数据

  public Result() {}

  public Result(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public Result(boolean success, String message, Object data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }

}
