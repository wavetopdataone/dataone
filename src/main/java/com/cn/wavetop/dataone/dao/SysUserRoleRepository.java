package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole,Long> {
    Integer deleteByUserId(Long userId);
}
