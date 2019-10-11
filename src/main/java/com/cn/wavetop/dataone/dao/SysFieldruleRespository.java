package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysDbinfo;
import com.cn.wavetop.dataone.entity.SysFieldrule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author yongz
 * @Date 2019/10/11、15:30
 */
public interface SysFieldruleRespository extends JpaRepository<SysFieldrule,Long> {
    boolean existsByJobId(long job_id);

    List<SysFieldrule> findByJobId(long job_id);
}
