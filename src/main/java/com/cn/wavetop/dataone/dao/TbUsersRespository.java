package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.TbUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TbUsersRespository extends JpaRepository<TbUsers,Long> {


    List<TbUsers> findByName(String name);

    List<TbUsers> findByNameAndPassword(String name,String password);
    @Transactional
    int deleteByName(String name);

}
