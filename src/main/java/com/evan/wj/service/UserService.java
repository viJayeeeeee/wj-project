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

//    public List<User> list(){
//        List<User> users=userDao.findAll();
//        users.forEach(user -> {
//            List<AdminRole> roles=adminRoleService.listRoleByUser(user.getUsername());
//            users.se
//        });
//    }
}
