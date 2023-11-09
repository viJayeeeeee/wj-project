package com.evan.wj.controller;

import com.evan.wj.pojo.AdminMenu;
import com.evan.wj.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;

    @CrossOrigin
    @GetMapping("/api/menu")
    public List<AdminMenu> menus() {
//        System.out.println("getMenu");
        return adminMenuService.getMenusByCurrentUser();
    }
}
