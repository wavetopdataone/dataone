package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.TbUsers;

public interface TbUsersService {

    //Object findByName(String name);
    Object addTbUsers(TbUsers tbUsers);
    Object login(String name,String password);
    Object findAll();
    Object delete(String name);
}
