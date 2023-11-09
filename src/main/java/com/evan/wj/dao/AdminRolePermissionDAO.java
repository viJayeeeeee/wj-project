package com.evan.wj.dao;

import com.evan.wj.pojo.AdminRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRolePermissionDAO extends JpaRepository<AdminRolePermission,Integer> {
    public List<AdminRolePermission> findAllByRid(List<Integer> rids);
}
