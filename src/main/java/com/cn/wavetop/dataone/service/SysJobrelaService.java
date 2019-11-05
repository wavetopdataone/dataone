package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.SysJobrela;

/**
 * @Author yongz
 * @Date 2019/10/12、10:52
 */
public interface SysJobrelaService {
    Object getJobrelaAll(Integer current,Integer size);

    Object checkJobinfoById(long id);

    Object addJobrela(SysJobrela sysJobrela);

    Object editJobrela(SysJobrela sysJobrela);

    Object deleteJobrela(Long id);

    Object jobrelaCount();

    Object queryJobrela(String job_name,Integer current ,Integer size);

    Object someJobrela(String job_status,Integer current ,Integer size);

    Object start(Long id);

    Object pause(Long id);

    Object end(Long id);

    //根据用户查询出该管理员下面的任务
    Object selJobrela(Integer current,Integer size);
    //根据用户查询出该管理员下面已分配的任务
    Object selJobrelaByUserId(Long userId,String name,Integer current,Integer size);

    Object selJobrelaUser(String status,String name);

    Object seleJobrelaByUser(Long userId);

    Object selUserByJobId(Long jobId);
    Object addUserByJobId(Long jobId,String userId);
    Object findById(Long jobId);
}
