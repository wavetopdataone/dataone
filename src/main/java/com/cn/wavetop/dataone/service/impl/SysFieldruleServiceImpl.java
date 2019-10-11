package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.SysFieldruleRespository;
import com.cn.wavetop.dataone.dao.SysJobrelaRespository;
import com.cn.wavetop.dataone.entity.SysFieldrule;
import com.cn.wavetop.dataone.entity.SysJobrela;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.SysFieldruleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yongz
 * @Date 2019/10/11、15:27
 */
@Service
public class SysFieldruleServiceImpl implements SysFieldruleService {
    @Autowired
    private SysFieldruleRespository repository;
    @Autowired
    private SysJobrelaRespository sysJobrelaRespository;


    @Override
    public Object getFieldruleAll() {
        return ToData.builder().status("1").data(repository.findAll()).build();
    }

    @Override
    public Object checkFieldruleByJobId(long job_id) {
        if (repository.existsByJobId(job_id)) {
            List<SysFieldrule> sysFieldrule = repository.findByJobId(job_id);
            Map<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("data", sysFieldrule);
            return map;
        } else {
            return ToData.builder().status("0").message("任务不存在").build();

        }
    }

    @Override
    public Object addFieldrule(SysFieldrule sysFieldrule) {
        if (sysFieldrule == null || sysFieldrule.getDestName().equals("")) {
            sysFieldrule.setDestName(sysFieldrule.getSourceName());
        }
        List<SysFieldrule> sysFieldrules = repository.findByJobId(sysFieldrule.getJobId());
        //List<SysJobrela> sysJobrelas = sysJobrelaRespository.findById(sysFieldrule.getJobId());

        return "";
    }
}
