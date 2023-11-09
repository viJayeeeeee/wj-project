package com.evan.wj.service;

import com.evan.wj.dao.AdminPermissionDAO;
import com.evan.wj.dao.AdminRolePermissionDAO;
import com.evan.wj.pojo.AdminPermission;
import com.evan.wj.pojo.AdminRole;
import com.evan.wj.pojo.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionService {
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;
    @Autowired
    AdminPermissionDAO adminPermissionDAO;

    public Set<String> listPermissionURLsByUser(String username) {
        List<Integer> rids = adminRoleService.listRoleByUser(username)
                .stream().map(AdminRole::getId).collect(Collectors.toList());
        List<Integer> pids = adminRolePermissionDAO.findAllByRid(rids)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        List<AdminPermission> perms = adminPermissionDAO.findAllById(pids);
        return perms.stream().map(AdminPermission::getUrl).collect(Collectors.toSet());
    }

    public boolean needFilter(String requestAPI) {
        List<AdminPermission> ps = adminPermissionDAO.findAll();
        for (AdminPermission p: ps) {
            if (p.getUrl().equals(requestAPI)) {
                return true;
            }
        }
        return false;
    }

}
