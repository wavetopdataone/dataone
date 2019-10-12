package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysTablerule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author yongz
 * @Date 2019/10/12、15:58
 */
public interface SysTableruleRespository extends JpaRepository<SysTablerule,Long> {
    SysTablerule findByJobIdAndSourceTable(Long job_id, String source_name);

}
