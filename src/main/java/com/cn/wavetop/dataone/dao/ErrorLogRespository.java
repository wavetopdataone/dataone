package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yongz
 * @Date 2019/10/11、11:20
 */
@Repository
public interface ErrorLogRespository extends JpaRepository<ErrorLog,Long> {

    List<ErrorLog> findAll();
    ErrorLog findById(long id);
    List<ErrorLog> findByJobNameContaining(String job_name);

    boolean existsByJobNameContaining(String job_name);

    void deleteByJobId(Long job_id);
}
