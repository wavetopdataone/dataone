package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.SysTableruleRepository;
import com.cn.wavetop.dataone.service.SysTableruleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysTableruleServiceImpl implements SysTableruleService {
    @Autowired
    private SysTableruleRepository sysTableruleRepository;
    @Override
    public Object findByJobId(String sourceTable, long jobId) {
        return null;
    }
}
