package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.SysUser;

public interface SysUserService  {

    Object login(String name,String password);
    Object loginOut();
    Object findAll();
    Object findById();
    Object update(SysUser sysUser);
    Object addSysUser(SysUser sysUser,String id);
    Object delete(String userName);
    Object findRolePerms(String userName);
    Object findByUserName(String userName);
    Object findUserByDept(Long deptId);
    Object updateUser(Long id,Long DeptId);
    Object updateStatus(Long id,String status);
    Object selSysUser(Long userId);
    Object HandedTeam(Long id,Long userId);
}
