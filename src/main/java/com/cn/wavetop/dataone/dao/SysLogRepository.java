package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysLogRepository extends JpaRepository<SysLog,Long> {

    List<SysLog> findByJobIdOrderByCreateDateDesc(Long jobId);
}
