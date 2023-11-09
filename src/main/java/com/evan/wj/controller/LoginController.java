package com.evan.wj.controller;

import com.evan.wj.result.Result;
import com.evan.wj.pojo.User;
import com.evan.wj.result.ResultFactory;
import com.evan.wj.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/api/authentication")
    @CrossOrigin
    public String authentication() {
        System.out.println("identity successful!");
        return "identity successful!";
    }

    @PostMapping(value = "api/login")
    @CrossOrigin
    public Result login(@RequestBody User requestUser, HttpSession session) {
        String username = requestUser.getUsername();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(username);
        } catch (AuthenticationException e) {
            return ResultFactory.buildFailResult("账号密码错误");
        }
    }

    @GetMapping(value = "api/logout")
    @CrossOrigin
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("成功登出");
    }

//    @GetMapping(value = "/api/admin/user")
//    public List<User> listUsers() throws Exception{
//        return userService.list();
//    }
}
