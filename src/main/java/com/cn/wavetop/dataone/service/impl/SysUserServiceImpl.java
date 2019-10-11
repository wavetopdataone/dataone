package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.SysUserRepository;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public Object findAll() {
        List<SysUser> sysUserList=sysUserRepository.findAll();
        return ToData.builder().status("1").data(sysUserList).build();
    }

    @Override
    public Object findById(long id) {
        List<SysUser> sysUserList=sysUserRepository.findById(id);
        if(sysUserList!=null&&sysUserList.size()>0){
            return ToData.builder().status("1").data(sysUserList).build();
        }else{
            return ToDataMessage.builder().status("0").message("没有找到").build();
        }
    }

    @Override
    public Object update(SysUser sysUser) {
        List<SysUser> sysUserList= sysUserRepository.findById(sysUser.getId());
        System.out.println(sysUserList);
        List<SysUser> userList=new ArrayList<SysUser>();
        if(sysUserList!=null&&sysUserList.size()>0){
            sysUserList.get(0).setId(sysUser.getId());
            sysUserList.get(0).setUsername(sysUser.getUsername());
            sysUserList.get(0).setMail(sysUser.getMail());
            sysUserList.get(0).setGroupId(sysUser.getGroupId());
            sysUserList.get(0).setState(sysUser.getState());
            sysUserList.get(0).setRemark(sysUser.getRemark());
            sysUserList.get(0).setPassword(sysUser.getPassword());
            SysUser user=  sysUserRepository.save(sysUserList.get(0));
            sysUserList= sysUserRepository.findById(user.getId());
            if(user!=null&&!"".equals(user)){
                return ToData.builder().status("1").data(sysUserList).message("修改成功").build();
            }else{
                return ToDataMessage.builder().status("0").message("修改失败").build();
            }

        }else{
            SysUser user= sysUserRepository.save(sysUser);

            userList.add(user);
            return ToData.builder().status("1").data(userList).message("添加成功").build();

        }

    }

    @Override
    public Object addSysUser(SysUser sysUser) {
        System.out.println(sysUser+"-----------"+sysUser.getId());
       if(sysUserRepository.findById(sysUser.getId())!=null&&sysUserRepository.findById(sysUser.getId()).size()>0){

           return ToDataMessage.builder().status("0").message("已存在").build();
       }else{
           SysUser user= sysUserRepository.save(sysUser);
           List<SysUser> userList=new ArrayList<SysUser>();
           userList.add(user);
           return ToData.builder().status("1").data(userList).message("添加成功").build();
       }

    }

    @Override
    public Object delete(long id) {
        List<SysUser> sysUserList= sysUserRepository.findById(id);
        if(sysUserList!=null&&sysUserList.size()>0){
            int result=sysUserRepository.deleteById(id);
            if(result>0){
               return ToDataMessage.builder().status("1").message("删除成功").build();
            }else{
               return ToDataMessage.builder().status("0").message("删除失败").build();
            }
        }else{
            return ToDataMessage.builder().status("0").message("没有删除对象").build();
        }

    }
}
