package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.TbUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbUsersRespository extends JpaRepository<TbUsers,Long> {


    List<TbUsers> findByName(String name);

}
