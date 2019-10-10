package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.TbUsersRespository;
import com.cn.wavetop.dataone.entity.TbUsers;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.TbUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbUsersServiceImpl implements TbUsersService {
   @Autowired
   private TbUsersRespository tbUsersRespository;

    @Override
    public Object addTbUsers(TbUsers tbUsers) {

        TbUsers tbUsersList=tbUsersRespository.save(tbUsers);
        if(tbUsersRespository.findByName(tbUsers.getName()).size()<=0){
            return ToData.builder().status("0").message("用户已存在").build();

        }else{
              return ToData.builder().status("1");
        }

    }
}
