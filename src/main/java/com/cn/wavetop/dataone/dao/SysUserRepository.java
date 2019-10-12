package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.TbUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    List<SysUser> findById(long id);

    @Transactional
    @Modifying
    @Query("delete from SysUser where id = :id")
    int deleteById(long id);
}
