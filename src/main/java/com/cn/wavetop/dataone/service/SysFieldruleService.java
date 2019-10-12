package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.SysDbinfo;
import com.cn.wavetop.dataone.entity.SysFieldrule;

/**
 * @Author yongz
 * @Date 2019/10/11、15:27
 */
public interface SysFieldruleService {
    Object getFieldruleAll();

    Object checkFieldruleByJobId(long id);

    Object addFieldrule(SysFieldrule sysFieldrule);

    Object editFieldrule(String list_data, String source_name,String dest_name,Long job_id);

    Object deleteFieldrule(String source_name);

    Object linkTableDetails(SysDbinfo sysDbinfo);

}
