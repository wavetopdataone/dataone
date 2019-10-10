package com.cn.wavetop.dataone.service;

import com.cn.wavetop.dataone.entity.DataChangeSettings;
import com.cn.wavetop.dataone.entity.vo.ToData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

/**
 * @Author yongz
 * @Date 2019/10/10、11:45
 */

public interface DataChangeSettingsService {

  Object getDataChangeSettingsAll();

  Object getCheckDataChangeByjobid(String job_id);
}
