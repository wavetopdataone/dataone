package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.DataChangeSettingsRespository;
import com.cn.wavetop.dataone.entity.DataChangeSettings;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
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
    public Object getDataChangeSettingsAll() {
        return ToData.builder().status("1").data( repository.findAll()).build();
    }

    @Override
    public Object getCheckDataChangeByjobid(long job_id) {
        List<DataChangeSettings> dataChangeSettings = repository.findByJobId(job_id);
        if (dataChangeSettings.size()<=0){
            return ToDataMessage.builder().status("0").message( "任务不存在").build();
        }else {
            return ToData.builder().status("1").data( dataChangeSettings).build();
        }


    }
}
