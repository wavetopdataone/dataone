package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.TbUsersRespository;
import com.cn.wavetop.dataone.entity.TbUsers;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.TbUsersService;
import com.cn.wavetop.dataone.util.MD5Util;
import com.cn.wavetop.dataone.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
public class TbUsersServiceImpl implements TbUsersService {
   @Autowired
   private TbUsersRespository tbUsersRespository;
    @Transactional
    @Override
    public Object addTbUsers(TbUsers tbUsers) {
        try{
            TbUsers tbUsersList=null;
            if(tbUsersRespository.findByName(tbUsers.getName())!=null&&tbUsersRespository.findByName(tbUsers.getName()).size()>0){
                return ToDataMessage.builder().status("0").message("用户已存在").build();

            }else{
                String password= Md5Utils.md5password(tbUsers.getPassword());
                tbUsers.setPassword(password);
                tbUsersList= tbUsersRespository.save(tbUsers);
                HashMap<Object, Object> map = new HashMap();
                map.put("status", "1");
                map.put("name", tbUsersList.getName());
                map.put("password", tbUsersList.getPassword());
                return map;
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }

    }

    @Override
    public Object login(String name,String password){
       List<TbUsers> tbUsersList= tbUsersRespository.findByNameAndPassword(name,Md5Utils.md5password(password));
        HashMap<Object, Object> map = new HashMap();
        if(tbUsersList!=null&&tbUsersList.size()>0){
           if(Md5Utils.md5password(password).equals(tbUsersList.get(0).getPassword())){
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
//        for(int i=0;i<tbUsersList.size();i++){
//            tbUsersList.get(i).setPassword(MD5Util.convertMD5(tbUsersList.get(i).getPassword()));
//        }
        return ToData.builder().status("1").data(tbUsersList).build();

    }
    @Transactional
    @Override
    public Object delete(String name){
        try{
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
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }


    }
}
