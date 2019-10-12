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
    List<SysJobrela> findById(long id);

    SysJobrela findByDestName(String name);

    SysJobrela findBySourceName(String name);

    boolean existsByDestNameOrSourceName(String name, String name1);

    boolean existsByDestIdOrSourceId(long id, long id1);
}
