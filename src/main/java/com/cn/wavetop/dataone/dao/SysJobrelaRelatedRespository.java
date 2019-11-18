package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysJobrelaRelated;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysJobrelaRelatedRespository extends JpaRepository<SysJobrelaRelated,Long> {

    List<SysJobrelaRelated> findByMasterJobId(Long masterJobId);
}
