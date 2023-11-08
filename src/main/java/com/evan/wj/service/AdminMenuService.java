package com.evan.wj.service;

import com.evan.wj.dao.AdminMenuDAO;
import com.evan.wj.pojo.AdminMenu;
import com.evan.wj.pojo.AdminRoleMenu;
import com.evan.wj.pojo.AdminUserRole;
import com.evan.wj.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;

    @Autowired
    UserService userService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminMenu> getAllByParentId(int pid) {
        return adminMenuDAO.findAllByParentId(pid);
    }

    public List<AdminMenu> getMenusByCurrentUser() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.getByUserName(username);

        List<Integer> rids = adminUserRoleService.listAllByUid(user.getId()).stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rids).stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());

        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());
        handleMenus(menus);
        return menus;
    }

    public void handleMenus(List<AdminMenu> menus) {
        for (AdminMenu menu : menus) {
            List<AdminMenu> children = getAllByParentId(menu.getId());
            menu.setChildren(children);
        }
//        Iterator<AdminMenu> iterator = menus.iterator();
//        while (iterator.hasNext()) {
//            AdminMenu menu = iterator.next();
//            if (menu.getParentId() != 0) iterator.remove();
//        }
        menus.removeIf(menu -> menu.getParentId() != 0);
    }
}
