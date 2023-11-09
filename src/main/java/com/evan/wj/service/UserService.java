package com.evan.wj.service;

import com.evan.wj.dao.UserDAO;
import com.evan.wj.pojo.AdminRole;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDao;
    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    public boolean isExit(String username) {
        User user = getByUserName(username);
        return null != user;
    }

    public User getByUserName(String username) {
        return userDao.findByUsername(username);
    }

    public User get(String username, String password) {
        return userDao.getByUsernameAndPassword(username, password);
    }

    public void add(User user) {
        userDao.save(user);
    }

    public List<User> list() {
        List<User> users = userDao.list();
        List<AdminRole> roles;
        for (User user : users) {
            roles = adminRoleService.listRoleByUser(user.getUsername());
            user.setRoles(roles);
        }
        return users;
    }

    public void editUser(User user) {
        User userInDB = userDao.findByUsername(user.getUsername());
        userInDB.setName(user.getName());
        userInDB.setPhone(user.getPhone());
        userInDB.setEmail(user.getEmail());
        userDao.save(userInDB);
        adminUserRoleService.saveRoleChanges(userInDB.getId(), user.getRoles());
    }

    public void updateUserStatus(User user) {
        User u = userDao.findByUsername(user.getUsername());
        u.setEnabled(user.isEnabled());
        userDao.save(u);
    }

}
