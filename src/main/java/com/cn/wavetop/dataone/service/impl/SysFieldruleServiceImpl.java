package com.cn.wavetop.dataone.service.impl;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.cn.wavetop.dataone.dao.*;
import com.cn.wavetop.dataone.entity.*;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysFieldruleService;
import com.cn.wavetop.dataone.util.DBConn;
import com.cn.wavetop.dataone.util.DBConns;
import com.cn.wavetop.dataone.util.DBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;


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
    @Autowired
    private SysTableruleRespository sysTableruleRespository;
    @Autowired
    private SysJobrelaRepository sysJobrelaRepository;
    @Autowired
    private SysDbinfoRespository sysDbinfoRespository;
    @Autowired
    private SysFieldruleRepository sysFieldruleRepository;
    @Autowired
    private SysFilterTableRepository sysFilterTableRepository;
    @Autowired
    private SysJobrelaRelatedRespository sysJobrelaRelatedRespository;
    @Autowired
    private SysDesensitizationRepository sysDesensitizationRepository;

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
        return "该接口已弃用";
    }

    @Transactional
    @Override
    public Object editFieldrule(String list_data, String source_name, String dest_name, Long job_id) {
        Map<Object, Object> map = new HashMap();
        SysFieldrule sysFieldrule1 = new SysFieldrule();
        List<SysFieldrule> sysFieldrules = new ArrayList<>();
        List<SysFieldrule> list = new ArrayList<>();
        String sql = "";
        String[] split;
        if (list_data != null && source_name != null && dest_name != null && !"undefined".equals(list_data) && !"undefined".equals(dest_name)) {

            split = list_data.replace("$", "@").split(",@,");

            SysTablerule byJobIdAndSourceTable = new SysTablerule();
            SysTablerule byJobIdAndSourceTable2 = null;

            SysDbinfo sysDbinfo = new SysDbinfo();
            //查詢关联的数据库连接表jobrela
            List<SysJobrela> sysJobrelaList = sysJobrelaRepository.findById(job_id.longValue());
            //查询到数据库连接
            if (sysJobrelaList != null && sysJobrelaList.size() > 0) {
                sysDbinfo = sysDbinfoRespository.findById(sysJobrelaList.get(0).getSourceId().longValue());
            } else {
                return ToDataMessage.builder().status("0").message("该任务没有连接").build();
            }
            List<SysJobrelaRelated> sysJobrelaRelateds= sysJobrelaRelatedRespository.findByMasterJobId(job_id);
            int a = sysTableruleRespository.deleteByJobIdAndSourceTable(job_id, source_name);
            if(sysJobrelaRelateds!=null&&sysJobrelaRelateds.size()>0) {
                for (SysJobrelaRelated sysJobrelaRelated : sysJobrelaRelateds) {
               sysTableruleRespository.deleteByJobIdAndSourceTable(sysJobrelaRelated.getSlaveJobId(), source_name);
               sysFieldruleRepository.deleteByJobIdAndSourceName(sysJobrelaRelated.getSlaveJobId(), source_name);
               sysFilterTableRepository.deleteByJobIdAndFilterTable(sysJobrelaRelated.getSlaveJobId(),source_name);

                }
                }
            if (!source_name.equals(dest_name)) {
                byJobIdAndSourceTable.setDestTable(dest_name);
                byJobIdAndSourceTable.setJobId(job_id);
                byJobIdAndSourceTable.setSourceTable(source_name);
                byJobIdAndSourceTable.setVarFlag(Long.valueOf(2));
                sysTableruleRespository.save(byJobIdAndSourceTable);
                //查询多任务
                if(sysJobrelaRelateds!=null&&sysJobrelaRelateds.size()>0) {
                    for (SysJobrelaRelated sysJobrelaRelated : sysJobrelaRelateds) {
//                        SysTablerule sysTablerule= sysTableruleRespository.findByJobId(sysJobrelaRelated.getSlaveJobId());
//                        if(sysTablerule!=null){
//                            continue;
//                        }else {
                        byJobIdAndSourceTable2=new SysTablerule();
                        byJobIdAndSourceTable2.setDestTable(dest_name);
                        byJobIdAndSourceTable2.setJobId(sysJobrelaRelated.getSlaveJobId());
                        byJobIdAndSourceTable2.setSourceTable(source_name);
                        byJobIdAndSourceTable2.setVarFlag(Long.valueOf(2));
                            sysTableruleRespository.save(byJobIdAndSourceTable2);
//                        }
                    }
                }
            }
            sysFieldruleRepository.deleteByJobIdAndSourceName(job_id, source_name);

            for (String s : split) {
                String[] ziduan = s.split(",");
                if (!ziduan[0].equals(ziduan[1])) {
                    SysFieldrule build = SysFieldrule.builder().fieldName(ziduan[0])
                            .destFieldName(ziduan[1])
                            .jobId(job_id)
                            .type(ziduan[2])
                            .scale(ziduan[3])
                            .notNull(Long.valueOf(ziduan[4]))
                            .accuracy(ziduan[5])
                            .sourceName(source_name)
                            .destName(dest_name).varFlag(Long.valueOf(2)).build();
                    sysFieldrules.add(repository.save(build));
                    if(sysJobrelaRelateds!=null&&sysJobrelaRelateds.size()>0) {
                        for(SysJobrelaRelated sysJobrelaRelated:sysJobrelaRelateds) {
//                            List<SysFieldrule> sysFieldrule= repository.findByJobId(sysJobrelaRelated.getSlaveJobId());
//                            if(sysFieldrule!=null&&sysFieldrule.size()>0){
//                                continue;
//                            }else {
                                SysFieldrule builds = SysFieldrule.builder().fieldName(ziduan[0])
                                        .destFieldName(ziduan[1])
                                        .jobId(sysJobrelaRelated.getSlaveJobId())
                                        .type(ziduan[2])
                                        .scale(ziduan[3])
                                        .notNull(Long.valueOf(ziduan[4]))
                                        .accuracy(ziduan[5])
                                        .sourceName(source_name)
                                        .destName(dest_name).varFlag(Long.valueOf(2)).build();
                                sysFieldrules.add(repository.save(builds));
//                            }
                        }
                    }
                }
            }
            if (sysDbinfo.getType() == 2) {
                sql = "select Column_Name as ColumnName,data_type as TypeName, (case when data_type = 'float' or data_type = 'int' or data_type = 'datetime' or data_type = 'bigint' or data_type = 'double' or data_type = 'decimal' then NUMERIC_PRECISION else CHARACTER_MAXIMUM_LENGTH end ) as length, NUMERIC_SCALE as Scale,(case when IS_NULLABLE = 'YES' then 0 else 1 end) as CanNull  from information_schema.columns where table_schema ='"+sysDbinfo.getDbname()+"' and table_name='" + source_name + "'";
            } else if (sysDbinfo.getType() == 1) {
                sql = "SELECT COLUMN_NAME, DATA_TYPE, NVL(DATA_LENGTH,0), NVL(DATA_PRECISION,0), NVL(DATA_SCALE,0), NULLABLE, COLUMN_ID ,DATA_TYPE_OWNER FROM DBA_TAB_COLUMNS WHERE TABLE_NAME='" + source_name
                        + "' AND OWNER='" + sysDbinfo.getSchema() + "'";
            }
            list = DBConns.getResult(sysDbinfo, sql, list_data);
            SysFilterTable sysFilterTable=null;
            sysFilterTableRepository.deleteByJobIdAndFilterTable(job_id,source_name);
            System.out.println("xuezihaoxuezihaoxuezihao"+list.size()+"---------------");
            for (SysFieldrule sysFieldrule : list) {
                sysFieldrule1 = new SysFieldrule();
                sysFilterTable=new SysFilterTable();
                sysFieldrule1.setFieldName(sysFieldrule.getFieldName());
                sysFieldrule1.setScale(sysFieldrule.getScale());
                sysFieldrule1.setType(sysFieldrule.getType());
                sysFieldrule1.setDestFieldName(sysFieldrule.getFieldName());
                sysFieldrule1.setJobId(job_id);
                sysFieldrule1.setSourceName(source_name);
                sysFieldrule1.setDestName(dest_name);
                sysFieldrule1.setVarFlag(Long.valueOf(1));
                SysFieldrule sysFieldrule2 = sysFieldruleRepository.save(sysFieldrule1);
                sysFilterTable.setFilterTable(source_name);
                sysFilterTable.setJobId(job_id);
                sysFilterTable.setFilterField(sysFieldrule.getFieldName());
                sysFilterTableRepository.save(sysFilterTable);
                if(sysJobrelaRelateds!=null&&sysJobrelaRelateds.size()>0) {
                    SysFilterTable sysFilterTable2=null;
                    for(SysJobrelaRelated sysJobrelaRelated:sysJobrelaRelateds) {
                        //判断是第一次添加还是修改
//                        List<SysFilterTable> sysFilterTable1= sysFilterTableRepository.findByJobIdAndFilterTable(sysJobrelaRelated.getSlaveJobId(),source_name);
//                        if(sysFilterTable1!=null&&sysFilterTable1.size()>0){
//                            continue;
//                        }else {
                        sysFilterTable2=new SysFilterTable();
                        sysFilterTable2.setFilterTable(source_name);
                        sysFilterTable2.setJobId(sysJobrelaRelated.getSlaveJobId());
                        sysFilterTable2.setFilterField(sysFieldrule.getFieldName());
                            sysFilterTableRepository.save(sysFilterTable2);
//                        }
                    }
                }
            }


                    long job_id1 = job_id;
                    SysJobrela sysJobrelaById = sysJobrelaRespository.findById(job_id1);
               sysJobrelaById.setJobStatus("0");

            List<SysJobrelaRelated> lists= sysJobrelaRelatedRespository.findByMasterJobId(job_id);
            if(lists!=null&&lists.size()>0) {
                for(SysJobrelaRelated sysJobrelaRelated:lists){
                    Optional<SysJobrela> sysJobrelaByIds = sysJobrelaRespository.findById(sysJobrelaRelated.getSlaveJobId());
                    sysJobrelaByIds.get().setJobStatus("0");
                }
        }

            map.put("status", 1);
            map.put("message", "保存成功");
            map.put("data", sysFieldrules);
        } else {
            map.put("status", 1);
            map.put("message", "保存成功");
        }
        return map;
    }

    @Transactional
    @Override
    public Object deleteFieldrule(String source_name) {
        Map<Object, Object> map = new HashMap();
        if (repository.deleteBySourceName(source_name)) {
            map.put("status", 1);
            map.put("message", "删除成功");
        } else {
            map.put("status", 0);
            map.put("message", "没有找到删除目标");
        }
        return map;
    }

    @Override
    public Object linkTableDetails(SysDbinfo sysDbinfo, String tablename,Long job_id) {
       HashMap<Object,Object> map=new HashMap<>();
        Long type = sysDbinfo.getType();
        String sql = "";
        ArrayList<Object> data = new ArrayList<>();
        List<SysFieldrule> list=new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        SysFieldrule sysFieldrule=null;
        if (type == 2) {
            sql = "select Column_Name as ColumnName,data_type as TypeName, (case when data_type = 'float' or data_type = 'int' or data_type = 'datetime' or data_type = 'bigint' or data_type = 'double' or data_type = 'decimal' then NUMERIC_PRECISION else CHARACTER_MAXIMUM_LENGTH end ) as length, NUMERIC_SCALE as Scale,(case when IS_NULLABLE = 'YES' then 0 else 1 end) as CanNull  from information_schema.columns where table_schema ='"+sysDbinfo.getDbname()+"' and table_name='" + tablename + "'";
            try {
                conn = DBConns.getMySQLConn(sysDbinfo);
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    sysFieldrule=new SysFieldrule();
                    sysFieldrule.setFieldName(rs.getString("ColumnName"));
                    sysFieldrule.setType(rs.getString("TypeName"));
                    if(rs.getString("length")!=null||!"".equals(rs.getString("length"))) {
                        sysFieldrule.setScale(rs.getString("length"));
                    }else{
                        sysFieldrule.setScale(rs.getString("0"));
                    }
                    sysFieldrule.setNotNull(Long.valueOf(rs.getString("CanNull")));
                    sysFieldrule.setAccuracy(rs.getString("Scale"));
                    System.out.println(sysFieldrule+"aaaaaaaaaaassssssss");

                    list.add(sysFieldrule);
                }
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                return "连接异常@！";
            }
        }
        else if (type == 1) {
            sql = "SELECT COLUMN_NAME, DATA_TYPE, NVL(DATA_LENGTH,0), NVL(DATA_PRECISION,0), NVL(DATA_SCALE,0), NULLABLE, COLUMN_ID ,DATA_TYPE_OWNER FROM DBA_TAB_COLUMNS WHERE TABLE_NAME='" + tablename + "' AND OWNER='" + sysDbinfo.getSchema() + "'";
            System.out.println(sql);
            try {
                conn = DBConns.getOracleConn(sysDbinfo);
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    sysFieldrule=new SysFieldrule();
                    sysFieldrule.setFieldName(rs.getString("COLUMN_NAME"));
                    sysFieldrule.setType(rs.getString("DATA_TYPE"));
                    if(rs.getString("NVL(DATA_LENGTH,0)")!=null||!"".equals(rs.getString("NVL(DATA_LENGTH,0)"))) {
                        sysFieldrule.setScale(rs.getString("NVL(DATA_LENGTH,0)"));
                    }else{
                        sysFieldrule.setScale(rs.getString("0"));
                    }
                    if(rs.getString("NULLABLE").equals("Y")){
                        sysFieldrule.setNotNull(Long.valueOf(0));
                    }else{
                        sysFieldrule.setNotNull(Long.valueOf(1));
                    }
                    sysFieldrule.setAccuracy(rs.getString("NVL(DATA_SCALE,0)"));
                    System.out.println(sysFieldrule+"eeeeeeeeeeeeeeeee");
                    list.add(sysFieldrule);
                }
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                return "连接异常@！";
            }
        }
        try {
            DBConns.close(stmt, conn, rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list+"---d");
        System.out.println(data);
      // List<SysFieldrule> sysFieldruleList= sysFieldruleRepository.findByJobIdAndSourceName(job_id,tablename);
      map.put("status","1");
      map.put("data",list);
        return map;
    }
    @Override
    public Object DestlinkTableDetails(SysDbinfo sysDbinfo, String tablename,Long job_id) {
      HashMap<Object,Object> map=new HashMap<>();
        ArrayList<Object> data = new ArrayList<>();
        List<SysFieldrule> sysFieldruleList= sysFieldruleRepository.findByJobIdAndSourceName(job_id,tablename);
        List<SysFieldrule> list=sysFieldruleRepository.findByJobIdAndSourceNameAndVarFlag(job_id,tablename,Long.valueOf(2));
        List<SysDesensitization> sysDesensitizations=new ArrayList<>();
            map.put("status","1");
            map.put("data", list);
        if(sysFieldruleList!=null&&sysFieldruleList.size()>0) {
            map.put("destName", sysFieldruleList.get(0).getDestName());
            sysDesensitizations=sysDesensitizationRepository.findByJobIdAndDestTable(job_id,sysFieldruleList.get(0).getDestName());
        }else{
            map.put("destName",null);
        }
        if(sysDesensitizations!=null&&sysDesensitizations.size()>0){
            map.put("data2",sysDesensitizations);
        }
        return map;
    }
}
