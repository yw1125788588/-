package com.imooc.demo.service;

import com.imooc.demo.entity.Result;
import com.imooc.demo.entity.User;

import java.util.List;

public interface UserService {
    /**	1.登录*/
    User login(User user);

    /**2.注册*/
    Result Register(User  user);

    /**3.根据id查询用户信息*/
    User getUserById(String uid);

    /**	1.查询所有用户*/
    List<User> findAllUser();

//    /**2.添加新的用户*/
//    Result addUser(User user);

    /**4.删除用户*/
    Result deleteUser(String uid);

    /**5.修改用户*/
    Result updateUser(User user);
}
