package com.cn.wavetop.dataone.service;

public interface SysLogService {

    Object findAll(Integer current,Integer size);
    Object findLogByCondition(Long deptId,String operation,String StartTime,String endTime,Integer current,Integer size);

}
