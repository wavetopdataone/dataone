package com.cn.wavetop.dataone.util;

import com.cn.wavetop.dataone.dao.SysUserRepository;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.vo.SysUserRoleVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class PermissionUtils {

    @Autowired
    private SysUserRepository sysUserRepository;

    public List<SysUserRoleVo> getPermission(){
        SysUser sysUser =(SysUser) SecurityUtils.getSubject().getPrincipal();
        List<SysUserRoleVo> list=sysUserRepository.findByLoginName(sysUser.getLoginName());
           return list;
    }
}
