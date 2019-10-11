package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.ErrorLog;
import com.cn.wavetop.dataone.entity.ErrorQueueSettings;

/**
 * @Author yongz
 * @Date 2019/10/11、11:17
 */
public interface ErrorLogService {
    Object getErrorlogAll();

    Object getCheckErrorLogById(long id);

    Object addErrorlog(ErrorLog errorLog);

    Object editErrorlog(ErrorLog errorLog);

    Object deleteErrorlog(long id);

    Object queryErrorlog(String job_name);
}
