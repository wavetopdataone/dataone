package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.SysUser;

public interface SysUserService  {

    Object findAll();
    Object findById(long id);
    Object update(SysUser sysUser);
    Object addSysUser(SysUser sysUser);
    Object delete(long id);
}
