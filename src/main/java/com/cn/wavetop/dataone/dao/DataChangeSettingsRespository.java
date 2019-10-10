package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.DataChangeSettings;
import com.cn.wavetop.dataone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author yongz
 */
@Repository
public interface DataChangeSettingsRespository extends JpaRepository<DataChangeSettings,Long> {
    /**
     * 根据用户名查找
     * @return
     */
    //@Query(value = "select * from user", nativeQuery = true)
    List<DataChangeSettings> findAll();

    List<DataChangeSettings> findByJobId(long job_id);
}
