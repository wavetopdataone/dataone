package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysTablerule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author yongz
 * @Date 2019/10/12、15:58
 */
public interface SysTableruleRespository extends JpaRepository<SysTablerule,Long> {
    @Modifying
    @Query("delete from SysTablerule where jobId = :job_id and sourceName=:source_name and varFlag=2")
    int deleteByJobIdAndSourceName(long job_id,String source_name);

}
