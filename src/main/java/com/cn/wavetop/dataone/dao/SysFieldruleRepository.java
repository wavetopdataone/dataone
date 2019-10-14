package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysFieldrule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysFieldruleRepository extends JpaRepository<SysFieldrule,Long> {
     List<SysFieldrule> findByJobId(long job_id);


    @Modifying
    @Query("delete from SysFieldrule where jobId = :job_id")
    int deleteByJobId(long job_id);
}