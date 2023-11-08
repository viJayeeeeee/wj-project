package com.evan.wj.dao;

import com.evan.wj.pojo.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminMenuDAO extends JpaRepository<AdminMenu, Integer> {
    List<AdminMenu> findAllByParentId(int pid);
}
