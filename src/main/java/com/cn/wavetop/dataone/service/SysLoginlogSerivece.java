package com.cn.wavetop.dataone.service;

public interface SysLoginlogSerivece {
    Object loginlogDept(Integer current, Integer size);

    Object findSysLoginlogByOperation(Long deptId, String operation, String startTime, String endTime, Integer current, Integer size);
}
