package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.config.shiro.MyShiroRelam;
import com.cn.wavetop.dataone.dao.SysMonitoringRepository;
import com.cn.wavetop.dataone.dao.SysRelaRepository;
import com.cn.wavetop.dataone.dao.SysTableruleRepository;
import com.cn.wavetop.dataone.entity.SysMonitoring;
import com.cn.wavetop.dataone.entity.SysRela;
import com.cn.wavetop.dataone.entity.SysTablerule;
import com.cn.wavetop.dataone.entity.vo.CountAndTime;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
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
    @Transactional
    @Override
    public Object update(SysMonitoring sysMonitoring) {
        try{
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
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }

    }
    @Transactional
    @Override
    public Object addSysMonitoring(SysMonitoring sysMonitoring) {
        try{
            System.out.println(sysMonitoring+"-----------"+sysMonitoring.getJobId());
            if(sysMonitoringRepository.findByJobId(sysMonitoring.getJobId())!=null&&sysMonitoringRepository.findByJobId(sysMonitoring.getJobId()).size()>0){

                return ToDataMessage.builder().status("0").message("已存在").build();
            }else{
                SysMonitoring user= sysMonitoringRepository.save(sysMonitoring);
                List<SysMonitoring> userList=new ArrayList<SysMonitoring>();
                userList.add(user);
                return ToData.builder().status("1").data(userList).message("添加成功").build();
            }

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }

    }
    @Transactional
    @Override
    public Object delete(long job_id) {
        try{
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
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
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
        CountAndTime countAndTime=new CountAndTime();
        if(sysMonitoringList!=null&&sysMonitoringList.size()>0) {
            for (SysMonitoring s : sysMonitoringList) {
                countAndTime.setSqlCount(s.getSqlCount());
                countAndTime.setOpTime(s.getOptTime());
                stringList.add(countAndTime);
//                stringList.add(s.getSqlCount());
//                stringList.add(s.getOptTime());
                System.out.println(s.getOptTime());
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
        long readData=0;
        long writeData=0;
        HashMap<Object, Object> map = new HashMap();
        if(sysMonitoringList!=null&&sysMonitoringList.size()>0) {
            for (SysMonitoring sysMonitoring:sysMonitoringList){
                if(sysMonitoring.getSqlCount()==null){
                    sysMonitoring.setSqlCount((long) 0);
                }
                if(sysMonitoring.getReadData()==null){
                    sysMonitoring.setReadData((long) 0);
                }
                if(sysMonitoring.getWriteData()==null){
                    sysMonitoring.setWriteData((long) 0);
                }
                if(sysMonitoring.getErrorData()==null){
                    sysMonitoring.setErrorData((long) 0);
                }
                readData+=sysMonitoring.getReadData();
                writeData+=sysMonitoring.getWriteData();
                errorDatas+=sysMonitoring.getErrorData();
            }
            map.put("read_datas",readData);
            map.put("write_datas",writeData);
            map.put("error_datas",errorDatas);
            map.put("status","1");
            return map;
        }else{
            map.put("read_datas","0");
            map.put("write_datas","0");
            map.put("error_datas","0");
            map.put("status","0");
            return map;
        }
    }
    @Transactional
    @Override
    public Object tableMonitoring(long job_id) {
        try{
            List<SysMonitoring> sysMonitoringList=sysMonitoringRepository.findByJobId(job_id);
            List<SysTablerule> sysTablerules=new ArrayList<SysTablerule>();
            List<SysMonitoring> sysMonitoringList1=new ArrayList<SysMonitoring>();

          if(sysMonitoringList!=null&&sysMonitoringList.size()>0) {
                for (SysMonitoring sysMonitoring:sysMonitoringList){
                    sysTablerules= sysTableruleRepository.findBySourceTableAndJobId(sysMonitoring.getSourceTable(),sysMonitoring.getJobId());
                    sysMonitoringList1=sysMonitoringRepository.findBySourceTableAndJobId(sysMonitoring.getSourceTable(),sysMonitoring.getJobId());
                    System.out.println(sysTablerules);
                    //如果目的表没有，去tablerule中找（找到的一定是修改过的）目标表，插到监控表里
                   // 如果tablerule中没有则代表源表和目的表是一致的;
                    if(sysMonitoringList1!=null&&sysMonitoringList1.size()>0) {
                        if (sysTablerules != null && sysTablerules.size()>0) {
                            sysMonitoringList1.get(0).setDestTable(sysTablerules.get(0).getSourceTable());

                        } else {
                            sysMonitoringList1.get(0).setDestTable(sysMonitoringList1.get(0).getSourceTable());
                        }
                        sysMonitoringRepository.save(sysMonitoringList1.get(0));
                    }
////                    for (SysTablerule sysTablerule:sysTablerules){
////                        System.out.println(sysMonitoringList1);
////                        sysMonitoringList1.get(0).setDestTable(sysTablerule.getDestTable());
////                        SysMonitoring S= sysMonitoringRepository.save(sysMonitoringList1.get(0));
////                        System.out.println(S);
////                    }
                }
                sysMonitoringList=sysMonitoringRepository.findByJobId(job_id);
                return ToData.builder().status("1").data(sysMonitoringList).build();
            }else{
                return ToDataMessage.builder().status("0").message("没有查到数据").build();
            }

        }catch (Exception e){
          return ToDataMessage.builder().status("0").message("发生错误").build();

        }

    }

    /**
     * 更新读监听数据
     * @param readData
     */
    @Transactional
    @Override
    public void updateReadMonitoring(long id, Long readData) {
        //List<SysMonitoring> byId = sysMonitoringRepository.findById(id);
        String table = "TEST";
        sysMonitoringRepository.updateReadMonitoring(id,readData,table);
    }
    /**
     * 更新写监听数据
     */
    @Transactional
    @Override
    public void updateWriteMonitoring (long id, Long writeData) {
        String table = "TEST";
        List<SysMonitoring> sysMonitoringList = sysMonitoringRepository.findByJobIdTable(id,table);
        if(sysMonitoringList != null && sysMonitoringList.size() > 0){
            for (SysMonitoring sysMonitoring : sysMonitoringList) {
                try {
                    Long readData = sysMonitoring.getReadData();
                    System.out.println("readData = " + readData);
                    System.out.println("writeData = " + writeData);
                    /*Long errorData = readData - writeData;
                    System.out.println("errorData = " + errorData);*/
                    sysMonitoringRepository.updateWriteMonitoring(id,writeData,table);
                } catch (Exception e){

                }
            }
        }

    }
}
