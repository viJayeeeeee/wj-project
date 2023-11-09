package com.evan.wj.service;

import com.evan.wj.dao.AdminRoleDAO;
import com.evan.wj.pojo.AdminRole;
import com.evan.wj.pojo.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRoleService {
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleDAO adminRoleDAO;
    public List<AdminRole> listRoleByUser(String username){
        int uid=userService.getByUserName(username).getId();
        List<Integer> rids=adminUserRoleService.listAllByUid(uid)
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        return adminRoleDAO.findAllById(rids);
    }
}
