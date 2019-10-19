package com.imooc.demo.controller;

import com.imooc.demo.entity.Result;
import com.imooc.demo.entity.User;
import com.imooc.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 1.登录
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public Result login(HttpServletRequest request, User user) {
        Result result = null;
        try {
            user = userService.login(user);// 如果登录成功返回 含有Id 的User
            if (user != null && !StringUtils.isEmpty(user.get Id())) {
                //将user保存到session中
                request.getSession().setAttribute("user", user);
                result = new Result(true, "登录成功", user);
            } else {
                result = new Result(false, "用户名或密码错误");
            }
        } catch (Exception e) {
            result = new Result(false, "登录失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 2.注册
     */
    @RequestMapping("/register.do")
    @ResponseBody
    public Result Register(User user) {
        Result result = null;
        try {
            result = userService.Register(user);
        } catch (Exception e) {
            result = new Result(false, "注册失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 3.根据id查询用户信息
     */
    @RequestMapping("/getUserById.do")
    @ResponseBody
    public User getUserById(String uid) {
        User user = null;
        try {
            if (StringUtils.isEmpty(uid)) {
                user = new User();
                user.setId("000");
                user.setUsername("无");
                return user;
            }
            user = userService.getUserById(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 4.注销
     */
    @RequestMapping("/logoutUser.do")
    @ResponseBody
    public void logoutUser(HttpServletRequest request) {
        try {
            request.getSession().removeAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*获得user对象*/
    @RequestMapping("/getUser.do")
    @ResponseBody
    public User Register(HttpServletRequest request) {
        User user = null;
        try {
            user = (User) request.getSession().getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /***
     * @description 删除用户信息
     *
     * @param uid
     * @date 2019/7/20 10:30
     * @return com.imooc.demo.entity.Result
     */
    @RequestMapping("/deleteUser.do")
    @ResponseBody
    public Result deleteUser(String uid) {
        Result result = null;
        try {
            result = userService.deleteUser(uid);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, e.getMessage());
        }
        return result;
    }

    /***
     * @description 修改用户信息
     *
     * @param user  修改后的用户
     * @date 2019/7/20 10:30
     * @return com.imooc.demo.entity.Result
     */
    @RequestMapping("/updateUser.do")
    @ResponseBody
    public Result updateUser(User user) {
        Result result = null;
        try {
            result = userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, e.getMessage());
        }
        return result;
    }

    /***
     * @description 查询所有用户
     *
     * @param
     * @date 2019/7/20 10:20
     * @return java.util.List<com.imooc.demo.entity.User>
     */
    public List<User> findAllUser() {
        List<User> users = null;
        try {
            users = userService.findAllUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
