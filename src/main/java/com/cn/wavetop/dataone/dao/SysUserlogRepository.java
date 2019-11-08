package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysUserlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserlogRepository extends JpaRepository<SysUserlog,Long> {
}
