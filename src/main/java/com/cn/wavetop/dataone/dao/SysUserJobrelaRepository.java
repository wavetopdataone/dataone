package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysUserJobrela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserJobrelaRepository extends JpaRepository<SysUserJobrela,Long> {
        boolean existsAllByUserId(Long userId);
    @Modifying
    @Query("delete from SysUserJobrela where jobrelaId = :jobrela_id")
        Integer deleteByJobrelaId(Long jobrela_id);
}
