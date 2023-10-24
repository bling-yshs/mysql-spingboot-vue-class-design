package com.yshs.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yshs.backend.entity.RestBean;
import com.yshs.backend.entity.User;
import com.yshs.backend.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-10-03
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @Resource
    IUserService userService;

    @PostMapping("/login")
    public RestBean<String> login(@RequestBody User user) {
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername());
        long count = userService.count(eq);
        if (count == 0) {
            return RestBean.failure(401, "用户名或密码错误");
        }
        User userDB = userService.getOne(eq);
        if (user.getPassword().equals(userDB.getPassword())) {
            return RestBean.success("登陆成功");
        } else {
            return RestBean.failure(401, "用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public RestBean<String> register(@RequestBody User user) {
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername());
        long count = userService.count(eq);
        if (count != 0) {
            return RestBean.failure(409, "该用户名已存在");
        }
        boolean b = userService.save(user);
        if (b) {
            return RestBean.success("注册成功");
        } else {
            return RestBean.failure(500, "未知错误");
        }
    }
}
