package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.SysMonitoringRepository;
import com.cn.wavetop.dataone.dao.SysRelaRepository;
import com.cn.wavetop.dataone.dao.SysTableruleRepository;
import com.cn.wavetop.dataone.entity.SysMonitoring;
import com.cn.wavetop.dataone.entity.SysRela;
import com.cn.wavetop.dataone.entity.SysTablerule;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SysMonitoringServiceImpl implements SysMonitoringService {
    @Autowired
    private SysMonitoringRepository sysMonitoringRepository;
    @Autowired
    private SysTableruleRepository sysTableruleRepository;

    @Override
    public Object findAll() {
        List<SysMonitoring> sysUserList=sysMonitoringRepository.findAll();
        return ToData.builder().status("1").data(sysUserList).build();
    }

    @Override
    public Object findByJobId(long job_id) {
        List<SysMonitoring> sysMonitoringList=sysMonitoringRepository.findByJobId(job_id);
        System.out.println(sysMonitoringList);
        if(sysMonitoringList!=null&&sysMonitoringList.size()>0){
            return ToData.builder().status("1").data(sysMonitoringList).build();
        }else{
            return ToDataMessage.builder().status("0").message("没有找到").build();
        }
    }

    @Override
    public Object update(SysMonitoring sysMonitoring) {
        List<SysMonitoring> sysMonitoringList= sysMonitoringRepository.findByJobId(sysMonitoring.getJobId());
        System.out.println(sysMonitoringList);
        List<SysMonitoring> userList=new ArrayList<SysMonitoring>();
        if(sysMonitoringList!=null&&sysMonitoringList.size()>0){
            //sysMonitoringList.get(0).setId(sysMonitoring.getId());
            sysMonitoringList.get(0).setJobId(sysMonitoring.getJobId());
            sysMonitoringList.get(0).setJobName(sysMonitoring.getJobName());
            sysMonitoringList.get(0).setSyncRange(sysMonitoring.getSyncRange());
            sysMonitoringList.get(0).setSourceTable(sysMonitoring.getSourceTable());
            sysMonitoringList.get(0).setDestTable(sysMonitoring.getDestTable());
            sysMonitoringList.get(0).setSqlCount(sysMonitoring.getSqlCount());
            sysMonitoringList.get(0).setOptTime(sysMonitoring.getOptTime());
            sysMonitoringList.get(0).setNeedTime(sysMonitoring.getNeedTime());
            sysMonitoringList.get(0).setFulldataRate(sysMonitoring.getFulldataRate());
            sysMonitoringList.get(0).setIncredataRate(sysMonitoring.getIncredataRate());
            sysMonitoringList.get(0).setStocksdataRate(sysMonitoring.getStocksdataRate());
            sysMonitoringList.get(0).setTableRate(sysMonitoring.getTableRate());
            sysMonitoringList.get(0).setReadRate(sysMonitoring.getReadRate());
            sysMonitoringList.get(0).setDisposeRate(sysMonitoring.getDisposeRate());
            sysMonitoringList.get(0).setJobStatus(sysMonitoring.getJobStatus());
            sysMonitoringList.get(0).setReadData(sysMonitoring.getReadData());
            sysMonitoringList.get(0).setWriteData(sysMonitoring.getWriteData());
            sysMonitoringList.get(0).setErrorData(sysMonitoring.getErrorData());

            SysMonitoring user=  sysMonitoringRepository.save(sysMonitoringList.get(0));
            System.out.println(user);
            userList= sysMonitoringRepository.findById(user.getId());
            if(user!=null&&!"".equals(user)){
                return ToData.builder().status("1").data(userList).message("修改成功").build();
            }else{
                return ToDataMessage.builder().status("0").message("修改失败").build();
            }

        }else{
            return ToDataMessage.builder().status("0").message("修改失败").build();

        }
    }

    @Override
    public Object addSysMonitoring(SysMonitoring sysMonitoring) {
        System.out.println(sysMonitoring+"-----------"+sysMonitoring.getJobId());
        if(sysMonitoringRepository.findByJobId(sysMonitoring.getJobId())!=null&&sysMonitoringRepository.findByJobId(sysMonitoring.getJobId()).size()>0){

            return ToDataMessage.builder().status("0").message("已存在").build();
        }else{
            SysMonitoring user= sysMonitoringRepository.save(sysMonitoring);
            List<SysMonitoring> userList=new ArrayList<SysMonitoring>();
            userList.add(user);
            return ToData.builder().status("1").data(userList).message("添加成功").build();
        }

    }

    @Override
    public Object delete(long job_id) {
        List<SysMonitoring> sysUserList= sysMonitoringRepository.findByJobId(job_id);
        if(sysUserList!=null&&sysUserList.size()>0){
            int result=sysMonitoringRepository.deleteByJobId(job_id);
            if(result>0){
                return ToDataMessage.builder().status("1").message("删除成功").build();
            }else{
                return ToDataMessage.builder().status("0").message("删除失败").build();
            }
        }else{
            return ToDataMessage.builder().status("0").message("任务不存在").build();
        }

    }

    @Override
    public Object findLike(String source_table, long job_id) {
        List<SysMonitoring> sysMonitoringList=sysMonitoringRepository.findBySourceTableContainingAndJobId(source_table,job_id);
            return ToData.builder().status("1").data(sysMonitoringList).build();
    }

    @Override
    public Object dataRate(long job_id) {
        List<SysMonitoring> sysMonitoringList=sysMonitoringRepository.findByJobId(job_id);
        List<Object> stringList=new ArrayList<Object>();
        if(sysMonitoringList!=null&&sysMonitoringList.size()>0) {
            for (SysMonitoring s : sysMonitoringList) {
                stringList.add(s.getSqlCount());
                stringList.add(s.getOptTime());
            }
            return ToData.builder().status("1").data(stringList).build();
        }else{
            return ToDataMessage.builder().status("0").message("没有找到").build();
        }
    }

    @Override
    public Object showMonitoring(long job_id) {
        List<SysMonitoring> sysMonitoringList=sysMonitoringRepository.findByJobId(job_id);
        //StringBuffer sum=new StringBuffer();
        long sum=0;
        long errorDatas=0;
        HashMap<Object, Object> map = new HashMap();
        if(sysMonitoringList!=null&&sysMonitoringList.size()>0) {
            for (SysMonitoring sysMonitoring:sysMonitoringList){
                sum+=sysMonitoring.getSqlCount();
                errorDatas+=sysMonitoring.getErrorData();
            }
            map.put("read_datas",sum);
            map.put("write_datas",sum);
            map.put("error_datas",errorDatas);

            return map;
        }else{
            map.put("read_datas","0");
            map.put("write_datas","0");
            map.put("error_datas","0");
            return map;
        }
    }

    @Override
    public Object tableMonitoring(long job_id) {
        List<SysMonitoring> sysMonitoringList=sysMonitoringRepository.findByJobId(job_id);
        List<SysTablerule> sysTablerules=new ArrayList<SysTablerule>();
        List<SysMonitoring> sysMonitoringList1=new ArrayList<SysMonitoring>();

        if(sysMonitoringList!=null&&sysMonitoringList.size()>0) {
            for (SysMonitoring sysMonitoring:sysMonitoringList){
                sysTablerules= sysTableruleRepository.findBySourceTableAndJobId(sysMonitoring.getSourceTable(),sysMonitoring.getJobId());
                System.out.println(sysTablerules);
                for (SysTablerule sysTablerule:sysTablerules){
                     sysMonitoringList1=sysMonitoringRepository.findBySourceTableAndJobId(sysMonitoring.getSourceTable(),sysMonitoring.getJobId());
                     System.out.println(sysMonitoringList1);
                     sysMonitoringList1.get(0).setDestTable(sysTablerule.getDestTable());
                     SysMonitoring S= sysMonitoringRepository.save(sysMonitoringList1.get(0));
                     System.out.println(S);
                }
            }
            sysMonitoringList=sysMonitoringRepository.findByJobId(job_id);
          return ToData.builder().status("1").data(sysMonitoringList).build();
        }else{
            return ToDataMessage.builder().status("0").message("没有查到数据").build();
        }
    }


}
