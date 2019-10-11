package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.DataChangeSettings;
import com.cn.wavetop.dataone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

    boolean existsByJobId(long jobId);


    @Modifying
    @Query("update DataChangeSettings u set u.deleteSyncingSource = :delete_syncing_source, u.deleteSync = :delete_sync,u.newSync = :new_sync,u.newtableSource = :newtable_source where u.jobId = :job_id")
    int updateByJobId(long job_id,long delete_syncing_source, long delete_sync, long new_sync,long newtable_source );


    int deleteByJobId(long job_id);
}
