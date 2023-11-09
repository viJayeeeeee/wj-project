package com.evan.wj.controller;

import com.evan.wj.pojo.User;
import com.evan.wj.result.Result;
import com.evan.wj.result.ResultFactory;
import com.evan.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody User requestUser){
        try {
            userService.editUser(requestUser);
        }catch (Exception e){
            return ResultFactory.buildFailResult(e.toString());
        }
        String message="修改用户信息成功！";
        return ResultFactory.buildSuccessResult(message);
    }
}
