package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.DataChangeSettingsRespository;
import com.cn.wavetop.dataone.dao.ErrorQueueSettingsRespository;
import com.cn.wavetop.dataone.entity.DataChangeSettings;
import com.cn.wavetop.dataone.entity.ErrorQueueSettings;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.ErrorQueueSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yongz
 * @Date 2019/10/11、10:30
 */
@Service
public class  ErrorQueueSettingsServiceImpl implements ErrorQueueSettingsService {
    @Autowired
    private ErrorQueueSettingsRespository repository;

    @Override
    public Object getErrorQueueAll() {
        return ToData.builder().status("1").data(repository.findAll()).build();
    }

    @Override
    public Object getCheckErrorQueueByjobid(long job_id) {

        if (repository.existsByJobId(job_id)) {
            ErrorQueueSettings errorQueueSettings = repository.findByJobId(job_id);
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
    public Object addErrorQueue(ErrorQueueSettings errorQueueSettings) {

        if (repository.existsByJobId(errorQueueSettings.getJobId())) {
            return ToData.builder().status("0").message("任务已存在").build();
        } else {
            ErrorQueueSettings saveData = repository.save(errorQueueSettings);
            HashMap<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("message", "添加成功");
            map.put("data", saveData);
            return map;
        }
    }
    @Transactional
    @Override
    public Object editErrorQueue(ErrorQueueSettings errorQueueSettings) {
        HashMap<Object, Object> map = new HashMap();
        ErrorQueueSettings data;
        long jobId = errorQueueSettings.getJobId();

        // 查看该任务是否存在，存在修改更新任务，不存在新建任务
        if (repository.existsByJobId(jobId)) {


            data = repository.findByJobId(jobId);
            data.setPauseSetup(errorQueueSettings.getPauseSetup());
            data.setPreSteup(errorQueueSettings.getPreSteup());
            data.setWarnSetup(errorQueueSettings.getWarnSetup());
           // data.setId(errorQueueSettings.getId());
            data = repository.save(data);

            map.put("status", 1);
            map.put("message", "修改成功");
            map.put("data", data);
        } else {
            data = repository.save(errorQueueSettings);
            map.put("status", 2);
            map.put("message", "添加成功");
            map.put("data", data);
        }
        return map;
    }
    @Transactional
    @Override
    public Object deleteErrorQueue(long job_id) {
        HashMap<Object, Object> map = new HashMap();

        // 查看该任务是否存在，存在删除任务，返回数据给前端
        if (repository.existsByJobId(job_id)) {
            int i = repository.deleteByJobId(job_id);
            if (i==1){
                map.put("status", 1);
                map.put("message", "删除成功");
            }else {
                map.put("status", 2);
                map.put("message", "删除失败");
            }

        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return map;
    }
}
