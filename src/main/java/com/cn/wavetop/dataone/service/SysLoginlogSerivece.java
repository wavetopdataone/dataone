package com.cn.wavetop.dataone.service;

public interface SysLoginlogSerivece {
    Object loginlogDept();

    Object findSysLoginlogByOperation(Long deptId, String operation, String startTime, String endTime);


    Object OutSysLoginlogByOperation(Long deptId, String operation, String startTime, String endTime);


}
