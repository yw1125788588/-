package com.imooc.demo.service.impl;

import com.imooc.demo.dao.UserMapper;
import com.imooc.demo.entity.Result;
import com.imooc.demo.entity.User;
import com.imooc.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 1.登录
     */
    @Override
    public User login(User user) {
        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return null;
        }
        User user1 = userMapper.selectUserByUsername(user.getUsername());
        if (user1 != null && user1.getPassword().equals(user.getPassword())) {
            return user1;
        }
        return null;
    }

    /**
     * 2.注册
     */
    @Override
    public Result Register(User user) {
        Result result = null;
        if (user == null
                || StringUtils.isEmpty(user.getUsername())
                || StringUtils.isEmpty(user.getPassword())) {
            result = new Result(false, "用户名和密码不能为空");
            return result;
        }
        // 判断用户名是否存在
        User tbUser = userMapper.selectUserByUsername(user.getUsername());
        if (tbUser == null || StringUtils.isEmpty(tbUser.getId())) {
            user.setId(UUID.randomUUID().toString());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userMapper.insert(user);
            result = new Result(true, "注册成功", user);
        } else {
            result = new Result(false, "用户名已存在");
        }
        return result;
    }

    /**
     * 3.根据id查询用户信息
     */
    @Override
    public User getUserById(String uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    /***
     * @description 查询所有用户
     *
     * @param
     * @date 2019/7/20 10:20
     * @return java.util.List<com.imooc.demo.entity.User>
     */
    public List<User> findAllUser() {
        List<User> users = userMapper.selectByExample(null);
        return users;
    }

//    @Override
//    public Result addUser(User user) {
//        return null;
//    }

    @Override
    /***
     * @description 删除用户信息
     *
     * @param uid
     * @date 2019/7/20 10:30
     * @return com.imooc.demo.entity.Result
     */
    public Result deleteUser(String uid) {
        if (StringUtils.isEmpty(uid)) {
            return new Result(false, "参数不能为空");
        }
        userMapper.deleteByPrimaryKey(uid);
        return new Result(true, "删除成功");
    }

    @Override
    /***
     * @description 修改用户信息
     *
     * @param user  修改后的用户
     * @date 2019/7/20 10:30
     * @return com.imooc.demo.entity.Result
     */
    public Result updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return new Result(true, "修改成功", user);
    }
}
