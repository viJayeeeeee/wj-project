package com.evan.wj.service;

import com.evan.wj.dao.AdminRoleMenuDAO;
import com.evan.wj.pojo.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminRoleMenuService {

    @Autowired
    AdminRoleMenuDAO adminRoleMenuDAO;

    List<AdminRoleMenu> findAllByRid(List<Integer> rids) {
        List<AdminRoleMenu> adminRoleMenusList = new ArrayList<>();
        for (Integer i : rids) {
            adminRoleMenusList.addAll(adminRoleMenuDAO.findAllByRid(i));
        }
        return adminRoleMenusList;
//        return adminRoleMenuDAO.findAllByRid(rids);
    }
}
