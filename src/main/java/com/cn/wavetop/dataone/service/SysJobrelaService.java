package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.SysJobrela;

/**
 * @Author yongz
 * @Date 2019/10/12、10:52
 */
public interface SysJobrelaService {
    Object getJobrelaAll();

    Object checkJobinfoById(long id);

    Object addJobrela(SysJobrela sysJobrela);

    Object editJobrela(SysJobrela sysJobrela);

    Object deleteJobrela(Long id);

    Object jobrelaCount();

    Object queryJobrela(String job_name);

    Object someJobrela(Long job_status);

    Object start(Long id);

    Object pause(Long id);

    Object end(Long id);

}
