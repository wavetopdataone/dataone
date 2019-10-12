package com.cn.wavetop.dataone.service.impl;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.cn.wavetop.dataone.dao.SysFieldruleRespository;
import com.cn.wavetop.dataone.dao.SysJobrelaRespository;
import com.cn.wavetop.dataone.dao.SysTableruleRespository;
import com.cn.wavetop.dataone.entity.SysDbinfo;
import com.cn.wavetop.dataone.entity.SysFieldrule;
import com.cn.wavetop.dataone.entity.SysJobrela;
import com.cn.wavetop.dataone.entity.SysTablerule;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.SysFieldruleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Object editFieldrule(String list_data, String source_name, String dest_name, Long job_id) {

        String[] split = list_data.split(",$,");
        if (dest_name == null || dest_name.equals("")) {
            dest_name = source_name;
        }
        repository.deleteByJobIdAndSourceName(job_id, source_name);
        ArrayList<SysFieldrule> sysFieldrules = new ArrayList<>();

        for (String s : split) {
            String[] ziduan = s.split(",");
            SysFieldrule build = SysFieldrule.builder().fieldName(ziduan[0])
                    .destFieldName(ziduan[1])
                    .jobId(job_id)
                    .type(ziduan[2])
                    .scale(ziduan[3])
                    .sourceName(source_name)
                    .destName(dest_name).build();

            sysFieldrules.add(repository.save(build));
            long job_id1 = job_id;
            SysJobrela sysJobrelaById = sysJobrelaRespository.findById(job_id1);
            sysJobrelaById.setJobStatus(Long.valueOf(0));
            SysTablerule byJobIdAndSourceTable = sysTableruleRespository.findByJobIdAndSourceTable(job_id, source_name);
            byJobIdAndSourceTable.setDestTable(dest_name);
            sysTableruleRespository.save(byJobIdAndSourceTable);

        }
        Map<Object, Object> map = new HashMap();
        map.put("status", 1);
        map.put("message", "修改成功");
        map.put("data", sysFieldrules);
        return map;
    }

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
    public Object linkTableDetails(SysDbinfo sysDbinfo) {
        Long type = sysDbinfo.getType();

        if ( type == 2 ){

        }
        if ( type == 1 ){

        }
        return  "待定！";
    }
}
