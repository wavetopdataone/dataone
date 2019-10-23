package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.SysUser;

public interface SysUserService  {

    Object login(String name,String password);
    Object loginOut();
    Object findAll();
    Object findById(long id);
    Object update(SysUser sysUser);
    Object addSysUser(SysUser sysUser);
    Object delete(String userName);
    Object findRolePerms(String userName);

}
