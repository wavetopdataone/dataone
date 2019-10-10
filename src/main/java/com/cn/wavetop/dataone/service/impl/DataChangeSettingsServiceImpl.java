package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.DataChangeSettingsRespository;
import com.cn.wavetop.dataone.dao.UserRepository;
import com.cn.wavetop.dataone.entity.DataChangeSettings;
import com.cn.wavetop.dataone.entity.UserTest;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.DataChangeSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ToData getDataChangeSettingsAll() {
        return ToData.builder().status("1").data( repository.findAll()).build();
    }

    @Override
    public ToData getCheckDataChangeByjobid(String job_id) {
        repository.findByJobId(job_id);
        return null;
    }
}
