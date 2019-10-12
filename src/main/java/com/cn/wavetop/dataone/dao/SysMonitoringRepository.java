package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysMonitoring;
import com.cn.wavetop.dataone.entity.SysRela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SysMonitoringRepository extends JpaRepository<SysMonitoring,Long> {

    List<SysMonitoring> findByJobId(long job_id);
    List<SysMonitoring> findById(long id);
    List<SysMonitoring> findBySourceTableContainingAndJobId(String source_table,long job_id);
    List<SysMonitoring> findBySourceTableAndJobId(String source_table,long job_id);


    @Modifying
    @Query("delete from SysMonitoring where jobId = :job_id")
    int deleteByJobId(long job_id);
}
