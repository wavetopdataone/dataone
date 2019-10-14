package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.SysFieldruleRepository;
import com.cn.wavetop.dataone.dao.SysJobrelaRepository;
import com.cn.wavetop.dataone.dao.SysTableruleRepository;
import com.cn.wavetop.dataone.entity.*;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysTableruleService;
import com.cn.wavetop.dataone.util.DBConn;
import com.cn.wavetop.dataone.util.DBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysTableruleServiceImpl implements SysTableruleService {
    @Autowired
    private SysTableruleRepository sysTableruleRepository;
    @Autowired
    private SysJobrelaRepository sysJobrelaRepository;
    @Autowired
    private SysFieldruleRepository sysFieldruleRepository;
    @Override
    public Object tableruleAll() {
        List<SysTablerule> sysUserList=sysTableruleRepository.findAll();
        return ToData.builder().status("1").data(sysUserList).build();
    }

    @Override
    public Object checkTablerule(long job_id) {
        List<SysTablerule> sysUserList=sysTableruleRepository.findByJobId(job_id);
        System.out.println(sysUserList);
        if(sysUserList!=null&&sysUserList.size()>0){
            List<SysJobrela> sysJobrelaList=sysJobrelaRepository.findById(job_id);
            if(sysJobrelaList!=null&&sysJobrelaList.size()>0) {
                sysJobrelaList.get(0).setJobStatus((long) 0);
                SysJobrela sysJobrela = sysJobrelaRepository.save(sysJobrelaList.get(0));
            }
            return ToData.builder().status("1").data(sysUserList).build();
        }else{
            return ToDataMessage.builder().status("0").message("没有该任务").build();
        }

    }
    @Transactional
    @Override
    public Object addTablerule(SysTablerule sysTablerule) {
        try{
            System.out.println(sysTablerule+"-----------"+sysTablerule.getId());
            List<SysTablerule> sysTableruleList =sysTableruleRepository.findByJobId(sysTablerule.getJobId());
            String[] b= sysTablerule.getSourceTable().split(",");
            List<SysTablerule> list=new ArrayList<SysTablerule>();
            String[]c=null;
            if(sysTableruleList!=null&&sysTableruleList.size()>0){
                return ToDataMessage.builder().status("0").message("任务已存在").build();
            }else{
                String destTable=sysTablerule.getDestTable();
                if(destTable!=null&&!"".equals(destTable)){
                    c= sysTablerule.getDestTable().split(",");
                }
                SysTablerule sysTablerule1=null;
                for(int i=0;i<b.length;i++){
                    if(c!=null&&c.length>0){
                        sysTablerule.setDestTable(c[i]);
                    }else{
                        destTable=b[i];
                        sysTablerule.setDestTable(destTable);
                    }
                    sysTablerule.setSourceTable(b[i]);
                    sysTablerule1= sysTableruleRepository.save(sysTablerule);
                    list.add(sysTablerule1);
                }
                return ToData.builder().status("1").data(list).build();
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }

    }
    @Transactional
    @Override
    public Object editTablerule(SysTablerule sysTablerule) {
        try{
            System.out.println(sysTablerule+"-----------"+sysTablerule.getId());
            List<SysTablerule> sysTableruleList =sysTableruleRepository.findByJobId(sysTablerule.getJobId());
            String[] b= sysTablerule.getSourceTable().split(",");
            List<SysTablerule> list=new ArrayList<SysTablerule>();
            String[]c=null;
            if(sysTableruleList!=null&&sysTableruleList.size()>0){
                int a= sysTableruleRepository.deleteByJobId(sysTablerule.getJobId());
                System.out.println(a);
                String destTable=sysTablerule.getDestTable();

                if(destTable!=null&&!"".equals(destTable)){
                    c= sysTablerule.getDestTable().split(",");
                }
                SysTablerule sysTablerule1=null;
                for(int i=0;i<b.length;i++){
                    if(c!=null&&c.length>0){
                        sysTablerule.setDestTable(c[i]);
                    }else{
                        destTable=b[i];
                        sysTablerule.setDestTable(destTable);
                    }
                    sysTablerule.setSourceTable(b[i]);
                    sysTablerule1= sysTableruleRepository.save(sysTablerule);
                    list.add(sysTablerule1);
                }
                return ToData.builder().status("1").message("修改成功").data(list).build();
            }else{
                String destTable=sysTablerule.getDestTable();
                if(destTable!=null&&!"".equals(destTable)){
                    c= sysTablerule.getDestTable().split(",");
                }
                SysTablerule sysTablerule1=null;
                for(int i=0;i<b.length;i++){
                    if(c!=null&&c.length>0){
                        sysTablerule.setDestTable(c[i]);
                    }else{
                        destTable=b[i];
                        sysTablerule.setDestTable(destTable);
                    }
                    sysTablerule.setSourceTable(b[i]);
                    sysTablerule1= sysTableruleRepository.save(sysTablerule);
                    list.add(sysTablerule1);
                }
                return ToData.builder().status("2").message("新增成功").data(list).build();
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }

    }

    @Transactional
    @Override
    public Object deleteTablerule(long job_id) {
        try{
            List<SysTablerule> sysTableruleList=sysTableruleRepository.findByJobId(job_id);
            List<SysFieldrule> sysFieldruleList=sysFieldruleRepository.findByJobId(job_id);
            if(sysTableruleList!=null&&sysTableruleList.size()>0){
                int result= sysTableruleRepository.deleteByJobId(job_id);
                if(sysFieldruleList!=null&&sysFieldruleList.size()>0) {
                    int result2 = sysFieldruleRepository.deleteByJobId(job_id);
                }
                return ToDataMessage.builder().status("1").message("删除成功").build();

            }else{
                return ToDataMessage.builder().status("0").message("任务不存在").build();
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }

    }

    @Override
    public Object linkDataTable(String type, String host, String user, String port, String password, String dbname, String schema) {

         DBHelper db1 = null;
         ResultSet ret = null;
         PreparedStatement pst = null;

        List<Object> list=new ArrayList<Object>();
        if("2".equals(type)) {
            String sql = "show tables";
            try {
                ret=DBHelper.getConnection(sql, host, user, password, port, dbname);//创建DBHelper对象
                System.out.println(ret);
                String tableName = null;
                while (ret.next()) {

                    tableName = ret.getString(1);
                    System.out.println(tableName);
                    list.add(tableName);
                }//显示数据

                return ToData.builder().data(list).build();
            } catch (SQLException e) {
                e.printStackTrace();
                return  ToDataMessage.builder().status("0").message("数据库连接错误").build();
            }finally {
                DBHelper.close(ret);//关闭连接
            }
        }
        else if("1".equals(type)){
            Connection con=DBConn.getConnection( host, user, password, port, dbname);
            String sql = "SELECT TABLE_NAME FROM DBA_ALL_TABLES WHERE OWNER='" + schema + "'AND TEMPORARY='N' AND NESTED='NO'";
            try {
                pst=con.prepareStatement(sql);
                ret=pst.executeQuery();
                String tableName = null;
                System.out.println("---------");

                while (ret.next()) {
                    System.out.println("---------"+ret.getString(1));
                    tableName = ret.getString(1);
                    list.add(tableName);
                    System.out.println(tableName);
                }//显示数据

                return ToData.builder().data(list).build();
            } catch (SQLException e) {
                e.printStackTrace();
                return  ToDataMessage.builder().status("0").message("数据库连接错误").build();
            }finally {
                try {
                    ret.close();
                    pst.close();
                    DBConn.close(con);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        else{

            return ToDataMessage.builder().status("0").message("类型不正确").build();
        }


    }
}
