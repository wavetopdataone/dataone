package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.DataChangeSettingsRespository;
import com.cn.wavetop.dataone.dao.ErrorLogRespository;
import com.cn.wavetop.dataone.dao.MailnotifySettingsRespository;
import com.cn.wavetop.dataone.dao.SysJobinfoRespository;
import com.cn.wavetop.dataone.entity.SysJobinfo;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.SysJobinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service

public class SysJobinfoServiceImpl implements SysJobinfoService {
    @Autowired
    private SysJobinfoRespository repository;
    @Autowired
    private DataChangeSettingsRespository dataChangeSettingsRespository;
    @Autowired
    private ErrorLogRespository errorLogRespository;
    @Autowired
    private MailnotifySettingsRespository mailnotifySettingsRespository;

    @Override
    public Object getJobinfoAll() {
        return ToData.builder().status("1").data(repository.findAll()).build();
    }

    @Override
    public Object checkJobinfoByJobId(long job_id) {

        List<SysJobinfo> sysJobinfos = repository.findByJobId(job_id);
        if (sysJobinfos == null || sysJobinfos.size() <= 0) {
            return ToData.builder().status("0").message("任务不存在").build();
        } else {
            return ToData.builder().status("1").data(sysJobinfos).build();
        }
    }
    @Transactional
    @Override
    public Object addJobinfo(SysJobinfo jobinfo) {
        long syncRange = jobinfo.getSyncRange();
        if (syncRange == 0) {
            jobinfo.setSyncRange(Long.valueOf(1));
        }
        if (repository.existsByJobId(jobinfo.getJobId())) {
            return ToData.builder().status("0").message("已存在").build();
        } else {
            SysJobinfo save = repository.save(jobinfo);
            HashMap<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("message", "添加成功");
            map.put("data", save);
            return map;
        }
    }
    @Transactional
    @Override
    public Object editJobinfo(SysJobinfo jobinfo) {
        HashMap<Object, Object> map = new HashMap();
        long syncRange = jobinfo.getSyncRange();
        if (syncRange == 0) {
            jobinfo.setSyncRange(Long.valueOf(1));
        }
        // 查看该任务是否存在，存在修改更新任务，不存在新建任务
        if (repository.existsByJobId(jobinfo.getJobId())) {
            SysJobinfo data = repository.findByJobId(Long.valueOf(jobinfo.getJobId()));
            data.setSyncRange(jobinfo.getSyncRange());
            //data.setId(jobinfo.getId());
            data.setJobId(jobinfo.getJobId());
            data.setBeginTime(jobinfo.getBeginTime());
            data.setDataEnc(jobinfo.getDataEnc());

            data.setDestCaseSensitive(jobinfo.getDestCaseSensitive());
            data.setDestWriteConcurrentNum(jobinfo.getDestWriteConcurrentNum());
            data.setEndTime(jobinfo.getEndTime());
            data.setMaxDestWrite(jobinfo.getMaxDestWrite());
            data.setMaxSourceRead(jobinfo.getMaxSourceRead());

            data.setPlayers(jobinfo.getPlayers());
            data.setReadBegin(jobinfo.getReadBegin());
            data.setReadWay(jobinfo.getReadWay());
            data.setSyncWay(jobinfo.getSyncWay());
            data.setReadFrequency(jobinfo.getReadFrequency());
            repository.save(data);
            map.put("status", 1);
            map.put("message", "修改成功");
            map.put("data", data);
        } else {
            SysJobinfo data = repository.save(jobinfo);
            map.put("status", 2);
            map.put("message", "添加成功");
            map.put("data", data);
        }
        return map;
    }

    @Transactional
    @Override
    public Object deleteJobinfo(Long job_id) {
        HashMap<Object, Object> map = new HashMap();
        // 查看该任务是否存在，存在删除任务，返回数据给前端
        if (repository.existsByJobId(job_id)) {
            repository.deleteByJobId(job_id);
            dataChangeSettingsRespository.deleteByJobId(job_id);
            errorLogRespository.deleteByJobId(job_id);
            mailnotifySettingsRespository.deleteByJobId(job_id);
            map.put("status", 1);
            map.put("message", "删除成功");
        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return map;
    }
}
