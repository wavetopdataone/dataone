package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.SysDesensitizationRepository;
import com.cn.wavetop.dataone.dao.SysJobrelaRelatedRespository;
import com.cn.wavetop.dataone.entity.SysDesensitization;
import com.cn.wavetop.dataone.entity.SysJobrelaRelated;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysDesensitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDesensitizationServiceImpl implements SysDesensitizationService {
    @Autowired
    private SysDesensitizationRepository sysDesensitizationRepository;
    @Autowired
    private SysJobrelaRelatedRespository sysJobrelaRelatedRespository;

    @Override
    public Object addDesensitization(SysDesensitization sysDesensitization) {
     List<SysDesensitization> list= sysDesensitizationRepository.findByJobIdAndDestTableAndDestField(sysDesensitization.getJobId(),sysDesensitization.getDestTable(),sysDesensitization.getDestField());
     if(list!=null&&list.size()>0) {
         //删除脱敏规则
         sysDesensitizationRepository.deleteByJobrelaId(sysDesensitization.getJobId(), sysDesensitization.getDestTable(), sysDesensitization.getDestField());
         //插入脱敏规则
         SysDesensitization sysDesensitization1 = sysDesensitizationRepository.save(sysDesensitization);
         //查询关联的任务
         List<SysJobrelaRelated> sysJobrelaRelateds = sysJobrelaRelatedRespository.findByMasterJobId(sysDesensitization.getJobId());
         if (sysJobrelaRelateds != null && sysJobrelaRelateds.size() > 0) {
             SysDesensitization s = null;
             for (SysJobrelaRelated sysJobrelaRelated : sysJobrelaRelateds) {
                 sysDesensitizationRepository.deleteByJobrelaId(sysJobrelaRelated.getSlaveJobId(),sysDesensitization.getDestTable(),sysDesensitization.getDestField());
                     s = new SysDesensitization();
                     s = sysDesensitization;
                     s.setJobId(sysJobrelaRelated.getSlaveJobId());
                     sysDesensitizationRepository.save(s);

             }
         }
     }else{

     }
        return ToDataMessage.builder().status("1").message("添加成功").build();
    }

    @Override
    public Object delDesensitization(SysDesensitization sysDesensitization) {
     List<SysDesensitization> list= sysDesensitizationRepository.findByJobIdAndDestTableAndDestField(sysDesensitization.getJobId(),sysDesensitization.getDestTable(),sysDesensitization.getDestField());
     if(list!=null&&list.size()>0) {
     sysDesensitizationRepository.deleteByJobrelaId(sysDesensitization.getJobId(), sysDesensitization.getDestTable(), sysDesensitization.getDestField());
     }
     List<SysJobrelaRelated> sysJobrelaRelateds= sysJobrelaRelatedRespository.findByMasterJobId(sysDesensitization.getJobId());
        if(sysJobrelaRelateds!=null&&sysJobrelaRelateds.size()>0){
            for(SysJobrelaRelated sysJobrelaRelated:sysJobrelaRelateds){
                sysDesensitizationRepository.deleteByJobrelaId(sysJobrelaRelated.getSlaveJobId(),sysDesensitization.getDestTable(),sysDesensitization.getDestField());
            }
        }
        return ToDataMessage.builder().status("1").message("删除成功").build();
    }

    public Object delJobrelaRelated(Long jobId){
        sysJobrelaRelatedRespository.deleteByMasterJobId(jobId);
        return "1";
    }
}
