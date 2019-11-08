package com.cn.wavetop.dataone.util;

import com.alibaba.fastjson.JSON;
import com.cn.wavetop.dataone.aop.MyLog;
import com.cn.wavetop.dataone.dao.*;
import com.cn.wavetop.dataone.entity.*;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class LogUtil {
    @Autowired
    private SysLogRepository sysLogRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysDeptRepository sysDeptRepository;
    @Autowired
    private SysUserlogRepository sysUserlogRepository;
    public  void addJoblog(SysJobrela sysJobrela,String method, String Operation){
        SysLog sysLog=new SysLog();
        sysLog.setCreateDate(new Date());
        if(PermissionUtils.getSysUser().getDeptId()!=0&&PermissionUtils.getSysUser().getDeptId()!=null) {
            //获取部门信息
            Optional<SysDept> sysDepts = sysDeptRepository.findById(PermissionUtils.getSysUser().getDeptId());
            String deptName = "";
            if (sysDepts != null) {
                deptName = sysDepts.get().getDeptName();
                sysLog.setDeptName(deptName);
            }
        }
        //获取角色信息
        List<SysRole> sysRoles= sysUserRepository.findUserById(PermissionUtils.getSysUser().getId());
        String roleName = "";
        if(sysRoles!=null&&sysRoles.size()>0) {
            roleName = sysRoles.get(0).getRoleName();
            sysLog.setRoleName(roleName);
        }
        sysLog.setIp(SecurityUtils.getSubject().getSession().getHost());
        sysLog.setMethod(method);
        String param=JSON.toJSONString(sysJobrela);
        sysLog.setParams(param);
        sysLog.setOperation(Operation);
        sysLog.setUsername(PermissionUtils.getSysUser().getLoginName());
        sysLog.setJobId(sysJobrela.getId());//任务id
        sysLog.setJobName(sysJobrela.getJobName());
        sysLogRepository.save(sysLog);
    }


    public void saveSysUserlog(Object o,String method, String Operation) {
        SysUserlog sysLog=new SysUserlog();
        sysLog.setCreateDate(new Date());
        if(PermissionUtils.getSysUser().getDeptId()!=0&&PermissionUtils.getSysUser().getDeptId()!=null) {
            //获取部门信息
            Optional<SysDept> sysDepts = sysDeptRepository.findById(PermissionUtils.getSysUser().getDeptId());
            String deptName = "";
            if (sysDepts != null) {
                deptName = sysDepts.get().getDeptName();
                sysLog.setDeptName(deptName);
            }
        }
        //获取角色信息
        List<SysRole> sysRoles= sysUserRepository.findUserById(PermissionUtils.getSysUser().getId());
        String roleName = "";
        if(sysRoles!=null&&sysRoles.size()>0) {
            roleName = sysRoles.get(0).getRoleName();
            sysLog.setRoleName(roleName);
        }
        sysLog.setIp(SecurityUtils.getSubject().getSession().getHost());//ip
        sysLog.setMethod(method);//方法路径
        String param=JSON.toJSONString(o);
        sysLog.setParams(param); //参数
        sysLog.setOperation(Operation);//操作
        sysLog.setUsername(PermissionUtils.getSysUser().getLoginName());//操作人
        SysUser sysUser=new SysUser();
        SysDept sysDept=new SysDept();
        if(o instanceof SysUser){
            sysUser=(SysUser) o;
               if("修改用户".equals(Operation)){
                   //String
               }

        }else if(o instanceof SysDept){
            sysDept=(SysDept) o;

        }else{

        }
        sysUserlogRepository.save(sysLog);
    }
}
