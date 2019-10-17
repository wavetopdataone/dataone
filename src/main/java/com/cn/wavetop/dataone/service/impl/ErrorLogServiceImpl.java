package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.ErrorLogRespository;
import com.cn.wavetop.dataone.entity.ErrorLog;
import com.cn.wavetop.dataone.entity.ErrorQueueSettings;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.ErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author yongz
 * @Date 2019/10/11、11:17
 */
@Service
public class ErrorLogServiceImpl  implements ErrorLogService {
    @Autowired
    private ErrorLogRespository repository;
    @Override
    public Object getErrorlogAll() {
        return ToData.builder().status("1").data(repository.findAll()).build();
    }

    @Override
    public Object getCheckErrorLogById(long id) {
        if (repository.existsById(id)) {
            ErrorLog errorQueueSettings = repository.findById(id);
            Map<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("data", errorQueueSettings);
            return map;
        } else {
            return ToData.builder().status("0").message("任务不存在").build();

        }
    }
    @Transactional
    @Override
    public Object addErrorlog(ErrorLog errorLog) {

        if (repository.existsById(errorLog.getId())) {
            return ToData.builder().status("0").message("任务已存在").build();
        } else {
            ErrorLog saveData = repository.save(errorLog);
            HashMap<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("message", "添加成功");
            map.put("data", saveData);
            return map;
        }
    }

    @Override
    public Object editErrorlog(ErrorLog errorLog) {
        HashMap<Object, Object> map = new HashMap();

        long id = errorLog.getId();

        // 查看该任务是否存在，存在修改更新任务，不存在新建任务
        if (repository.existsById(id )) {
            ErrorLog data = repository.findById(id);
            data.setJobId(errorLog.getJobId());
            data.setContent(errorLog.getContent());
            data.setDestName(errorLog.getDestName());
            data.setJobName(errorLog.getJobName());
            data.setOptContext(errorLog.getOptContext());
            data.setOptTime(errorLog.getOptTime());
            data.setOptType(errorLog.getOptType());
            data.setSchame(errorLog.getSchame());
            data.setSourceName(errorLog.getSourceName());
            data = repository.save(data);

            map.put("status", 1);
            map.put("message", "修改成功");
            map.put("data", data);
        } else {
            ErrorLog data = repository.save(errorLog);
            map.put("status", 2);
            map.put("message", "添加成功");
            map.put("data", data);
        }
        return map;
    }

    @Override
    public Object deleteErrorlog(long id) {
        HashMap<Object, Object> map = new HashMap();

        // 查看该任务是否存在，存在删除任务，返回数据给前端
        if (repository.existsById(id)) {
            repository.deleteById(id);
            map.put("status", 1);
            map.put("message", "删除成功");
        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return map;
    }

    @Override
    public Object queryErrorlog(String job_name) {
        HashMap<Object, Object> map = new HashMap();
        List<ErrorLog> data = repository.findByJobNameContaining(job_name);
        if (data != null) {
            map.put("status", 1);
            map.put("data", data);
        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return  map;
    }
}
