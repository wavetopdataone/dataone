package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.SysMonitoring;
import com.cn.wavetop.dataone.entity.SysRela;

public interface SysMonitoringService {
    Object findAll();
    Object findByJobId(long job_id);
    Object update(SysMonitoring sysMonitoring);
    Object addSysMonitoring(SysMonitoring sysMonitoring);
    Object delete(long job_id);

    Object findLike(String source_table,long job_id);
    Object dataRate(long job_id);
    Object showMonitoring(long job_id);
    Object tableMonitoring(long job_id);

    void updateReadMonitoring(long id, Long readData);

    void updateWriteMonitoring(long id, Long writeData);
}
