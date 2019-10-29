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
      private static   Subject subjects=null;
    public static void setSubject(Subject subject){
        subjects=subject;
    }
    //拿到登录的用户
    public static SysUser getSysUser(){
        SysUser sysUser =(SysUser)subjects.getPrincipal();
        System.out.println(sysUser+"-----------------------");
           return sysUser;
    }

    public static boolean isPermitted(String perms){

        return subjects.isPermitted(perms);
    }

    //邮箱验证
    public static boolean flag(String email) {
    // TODO Auto-ge 
            String regex="";
    //电子邮件  
    String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    return email.matches(check);
    }
}
