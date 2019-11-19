package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysFilterTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysFilterTableRepository extends JpaRepository<SysFilterTable,Long> {
    @Modifying
    @Query("delete from SysFilterTable where jobId = :job_id")
    int deleteByJobId(long job_id);
    int deleteByJobIdAndFilterTable(Long job_id,String filterTable);
    SysFilterTable findByJobId(Long job_id);
    List<SysFilterTable> findByJobIdAndFilterTable(Long job_id, String filterTable);

}
