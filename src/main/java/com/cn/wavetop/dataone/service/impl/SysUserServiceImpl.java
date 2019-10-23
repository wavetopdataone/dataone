package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.config.shiro.CredentialMatcher;
import com.cn.wavetop.dataone.dao.SysUserRepository;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.vo.SysUserRoleVo;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserRepository sysUserRepository;


    @Override
    public Object login(String name, String password) {
        List<SysUser> list=sysUserRepository.findAllByLoginName(name);
        List<SysUserRoleVo> s=new ArrayList<>();
        UsernamePasswordToken token=new UsernamePasswordToken(name,password);
        Subject subject= SecurityUtils.getSubject();
        Map<Object,Object> map=new HashMap<>();
        try{
            if(list!=null&&list.size()>0) {
                if(list.get(0).getStatus().equals("1")) {
                    subject.login(token);
                    //查的是用户角色权限三张表
                    s = sysUserRepository.findByLoginName(name);
//                    //比较用户是否有super这个权限
//                    System.out.println("-------------"+SecurityUtils.getSubject().isPermitted("super"));
//
                    map.put("status", "1");
                    map.put("data", s);
                    map.put("message", "登录成功");
                }else{
                    map.put("status", "3");
                    map.put("message", "账号被冻结");
                }

            }else{
                map.put("status", "0");
                map.put("message", "用户不存在");
            }
        }catch (Exception e){
            map.put("status","2");
            map.put("message","密码错误");
        }
        return map;
    }

    //退出
    @Override
    public Object loginOut() {
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        return ToDataMessage.builder().status("1").message("退出成功").build();
    }

    @Override
    public Object findAll() {
        sysUserRepository.findAll();
        return null;
    }

    @Override
    public Object findById(long id) {
        return null;
    }

    @Override
    public Object update(SysUser sysUser) {
        return null;
    }

    @Transactional
    @Override
    public Object addSysUser(SysUser sysUser) {
//        if(SecurityUtils.getSubject().isPermitted("super")||SecurityUtils.getSubject().isPermitted("admin")){
//            if(SecurityUtils.getSubject().isPermitted("super")){}
//        }
       List<SysUser> list=sysUserRepository.findAllByLoginName(sysUser.getLoginName());
        HashMap<String,String> map=new HashMap<>();
        if(list!=null&&list.size()>0) {
            map.put("status","0");
            map.put("message","用户已存在");
        }else{
            String[] saltAndCiphertext = CredentialMatcher.encryptPassword(sysUser.getPassword());
            sysUser.setSalt(saltAndCiphertext[0]);
            sysUser.setPassword(saltAndCiphertext[1]);
            sysUser.setStatus("1");
            sysUser.setCreateTime(new Date());
            SysUser suser = sysUserRepository.save(sysUser);
            map.put("status","1");
            map.put("message","添加成功");
        }
        return map;

    }
    @Transactional
    @Override
    public Object delete(String name) {
        try{
            if(sysUserRepository.findAllByLoginName(name)!=null&&sysUserRepository.findAllByLoginName(name).size()>0) {
                int result= sysUserRepository.deleteByLoginName(name);
                if(result>0){
                    return ToDataMessage.builder().status("1").message("删除成功").build();
                }else{
                    return ToDataMessage.builder().status("2").message("删除失败").build();
                }
            }else{
                return ToDataMessage.builder().status("0").message("用户不存在").build();
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }

    }

    //根据用户名查找角色权限
    @Override
    public Object findRolePerms(String userName) {
        List<SysUserRoleVo> s=  sysUserRepository.findByLoginName(userName);
        return s;
    }
}
