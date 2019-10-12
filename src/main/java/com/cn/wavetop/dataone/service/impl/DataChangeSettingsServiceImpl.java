package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.DataChangeSettingsRespository;
import com.cn.wavetop.dataone.entity.DataChangeSettings;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.DataChangeSettingsService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author yongz
 * @Date 2019/10/10、11:45
 */
@Service
public class DataChangeSettingsServiceImpl implements DataChangeSettingsService {
    @Autowired
    private DataChangeSettingsRespository repository;

    @Override
    public Object getDataChangeSettingsAll() {
        return ToData.builder().status("1").data(repository.findAll()).build();
    }

    @Override
    public Object getCheckDataChangeByjobid(long job_id) {
        List<DataChangeSettings> dataChangeSettings = repository.findByJobId(job_id);
        if (dataChangeSettings.size() <= 0) {
            return ToData.builder().status("0").message("任务不存在").build();
        } else {
            return ToData.builder().status("1").data(dataChangeSettings).build();
        }


    }

    @Transactional
    @Override
    public Object addDataChange(DataChangeSettings dataChangeSettings) {

        if (repository.existsByJobId(dataChangeSettings.getJobId())) {
            return ToData.builder().status("0").message("任务已存在").build();
        } else {
            DataChangeSettings saveData = repository.save(dataChangeSettings);
            HashMap<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("message", "添加成功");
            map.put("data", saveData);
            return map;
        }
    }

    @Transactional
    @Override
    public Object editDataChange(DataChangeSettings dataChangeSettings) {
        HashMap<Object, Object> map = new HashMap();
        DataChangeSettings data;


        // 查看该任务是否存在，存在修改更新任务，不存在新建任务
        if (repository.existsByJobId(dataChangeSettings.getJobId())) {
            repository.updateByJobId(dataChangeSettings.getJobId(), dataChangeSettings.getDeleteSyncingSource(), dataChangeSettings.getDeleteSync(), dataChangeSettings.getNewSync(), dataChangeSettings.getNewtableSource());
            data = repository.findByJobId(dataChangeSettings.getJobId()).get(0);
            map.put("status", 1);
            map.put("message", "修改成功");
            map.put("data", data);
        } else {
            data = repository.save(dataChangeSettings);
            map.put("status", 2);
            map.put("message", "添加成功");
            map.put("data", data);
        }
        return map;
    }

    @Transactional
    @Override
    public Object deleteDataChange(long job_id) {
        HashMap<Object, Object> map = new HashMap();

        // 查看该任务是否存在，存在删除任务，返回数据给前端
        if (repository.existsByJobId(job_id)) {
            int i = repository.deleteByJobId(job_id);
            map.put("status", 1);
            map.put("message", "删除成功");


        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return map;
    }
}
