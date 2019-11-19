package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysDesensitization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysDesensitizationRepository extends JpaRepository<SysDesensitization,Long> {
    @Modifying
    @Query("delete from SysDesensitization where jobId = :jobId and destTable=:destTable and destField=:destFiled")
    Integer deleteByJobrelaId(Long jobId,String destTable,String destFiled);

    List<SysDesensitization> findByJobIdAndDestTableAndDestField(Long jobId,String destTable,String destFiled);
    List<SysDesensitization> findByJobIdAndDestTable(Long jobId,String destTable);
    List<SysDesensitization> findByJobIdAndSourceTable(Long jobId,String sourceTable);

}
