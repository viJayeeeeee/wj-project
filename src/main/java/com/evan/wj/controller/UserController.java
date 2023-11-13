package com.evan.wj.controller;

import com.evan.wj.pojo.User;
import com.evan.wj.result.Result;
import com.evan.wj.result.ResultFactory;
import com.evan.wj.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody User requestUser) {
        try {
            userService.editUser(requestUser);
        } catch (Exception e) {
            return ResultFactory.buildFailResult(e.toString());
        }
        String message = "修改用户信息成功！";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping(value = "/api/admin/user")
    public List<User> listUsers() throws Exception {
        return userService.list();
    }

    @PutMapping("/api/admin/user/status")
    public Result changeUserStatus(@RequestBody User u) {
        System.out.println("修改用户状态");
        try {
            userService.updateUserStatus(u);
        } catch (Exception e) {
            return ResultFactory.buildFailResult(e.toString());
        }
        String message = "修改用户状态成功！";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/user/resetPassword")
    public Result changeUserPassword(@RequestBody User user) {
        System.out.println("修改密码");
        try {
            String salt=new SecureRandomNumberGenerator().nextBytes().toString();
            int times = 2;
            String encodedPassword = new SimpleHash("md5", user.getPassword(), salt, times).toString();

            user.setSalt(salt);
            user.setPassword(encodedPassword);
            userService.updateUserPassword(user);
        } catch (Exception e) {
            return ResultFactory.buildFailResult(e.toString());
        }
        String message = "修改用户密码成功！";
        return ResultFactory.buildSuccessResult(message);
    }
}
