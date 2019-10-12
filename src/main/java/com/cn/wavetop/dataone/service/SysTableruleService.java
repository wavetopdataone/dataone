package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.SysTablerule;

public interface SysTableruleService {
    Object tableruleAll();
    Object checkTablerule(long job_id);
    Object addTablerule(SysTablerule sysTablerule);
    Object editTablerule(SysTablerule sysTablerule);
    Object deleteTablerule(long job_id);
    Object linkDataTable(String type,String host,String user,String port,String password,String dbname,String schema);
}
