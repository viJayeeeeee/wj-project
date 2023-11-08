package com.evan.wj.dao;

import com.evan.wj.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
//    使用JPA，自动构建SQL语句

    //    通过username字段查询对应行并返回给User类
    User findByUsername(String username);

    User getByUsernameAndPassword(String username, String password);
}
