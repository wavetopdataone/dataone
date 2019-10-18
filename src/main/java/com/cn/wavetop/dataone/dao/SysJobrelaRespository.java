package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysFieldrule;
import com.cn.wavetop.dataone.entity.SysJobrela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @Author yongz
 * @Date 2019/10/11、15:47
 */
public interface SysJobrelaRespository   extends JpaRepository<SysJobrela,Long> {

    boolean existsByDestNameOrSourceName(String name, String name1);

    boolean existsByDestIdOrSourceId(long id, long id1);
    List<SysJobrela> findAllByOrderByIdDesc();

    SysJobrela  findById(long id);

    SysJobrela findByJobName(String jobName);

    boolean existsByIdOrJobName(long id, String jobName);

    SysJobrela findByIdOrJobName(long id, String jobName);

    int countByJobStatus(long i);

    List<SysJobrela> findByJobNameContainingOrderByIdDesc(String job_name);

    List<SysJobrela> findByJobStatus(Long job_status);

    boolean existsByJobName(String jobName);
}
