package com.cn.wavetop.dataone.service;

public interface SysUserlogService {
    Object findAllSysUserlog(Integer current, Integer size);
//    Object findSysUserlogByOperation(Long deptId,String operation,Integer current, Integer size);
    Object findLog(Long deptId,String operation,String StartTime,String endTime,Integer current,Integer size);
}
