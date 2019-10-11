package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysTablerule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysTableruleRepository extends JpaRepository<SysTablerule,Long> {

    List<SysTablerule> findBySourceTableAndJobId(String sourceTable,long jobId);
}
