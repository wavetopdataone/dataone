package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysLoginlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLoginlogRepository extends JpaRepository<SysLoginlog,Long> {
}
