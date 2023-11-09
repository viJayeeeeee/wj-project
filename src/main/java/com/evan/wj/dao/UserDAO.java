package com.evan.wj.dao;

import com.evan.wj.pojo.User;
import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Integer> {
//    使用JPA，自动构建SQL语句

    //    通过username字段查询对应行并返回给User类
    User findByUsername(String username);

    User getByUsernameAndPassword(String username, String password);

    @Query(value = "select new User(u.id,u.username,u.name,u.phone,u.email,u.enabled) from User u")
    List<User> list();
}
