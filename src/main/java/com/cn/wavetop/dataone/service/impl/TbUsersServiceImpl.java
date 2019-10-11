package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.TbUsersRespository;
import com.cn.wavetop.dataone.entity.TbUsers;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.TbUsersService;
import com.cn.wavetop.dataone.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TbUsersServiceImpl implements TbUsersService {
   @Autowired
   private TbUsersRespository tbUsersRespository;

    @Override
    public Object addTbUsers(TbUsers tbUsers) {

        TbUsers tbUsersList=null;
        if(tbUsersRespository.findByName(tbUsers.getName())!=null&&tbUsersRespository.findByName(tbUsers.getName()).size()>0){
            return ToDataMessage.builder().status("0").message("用户已存在").build();

        }else{
           String password= MD5Util.convertMD5(tbUsers.getPassword());
            tbUsers.setPassword(password);
            tbUsersList= tbUsersRespository.save(tbUsers);
            HashMap<Object, Object> map = new HashMap();
            map.put("status", "1");
            map.put("name", tbUsersList.getName());
            map.put("password", MD5Util.convertMD5(tbUsersList.getPassword()));
              return map;
        }
    }
    @Override
    public Object login(String name,String password){
       List<TbUsers> tbUsersList= tbUsersRespository.findByNameAndPassword(name,MD5Util.convertMD5(password));
        HashMap<Object, Object> map = new HashMap();
        if(tbUsersList!=null&&tbUsersList.size()>0){
           if(password.equals(MD5Util.convertMD5(tbUsersList.get(0).getPassword()))){
            map.put("status","1");
            map.put("name",name);
               return map;
           }else{
               return ToDataMessage.builder().status("2").message("密码错误").build();
           }
        }else{
            return ToDataMessage.builder().status("0").message("用户不存在").build();
        }
    }
    @Override
    public Object findAll(){

        List<TbUsers> tbUsersList=tbUsersRespository.findAll();
        for(int i=0;i<tbUsersList.size();i++){
            tbUsersList.get(i).setPassword(MD5Util.convertMD5(tbUsersList.get(i).getPassword()));
        }
        return ToData.builder().status("1").data(tbUsersList).build();

    }

    @Override
    public Object delete(String name){
        if(tbUsersRespository.findByName(name)!=null&&tbUsersRespository.findByName(name).size()>0) {
           int result= tbUsersRespository.deleteByName(name);
            if(result>0){
                return ToDataMessage.builder().status("1").message("删除成功").build();
            }else{
                return ToDataMessage.builder().status("2").message("删除失败").build();
            }
        }else{
            return ToDataMessage.builder().status("0").message("用户不存在").build();
        }

    }
}
