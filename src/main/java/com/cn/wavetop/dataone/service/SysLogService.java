package com.cn.wavetop.dataone.service;

public interface SysLogService {

    Object findAll();
    Object findLogByCondition(Long deptId,String operation,String StartTime,String endTime);
    Object OutSyslogByOperation(Long deptId, String operation, String startTime, String endTime);

}
