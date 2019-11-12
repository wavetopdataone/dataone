package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.service.SysLogService;
import com.cn.wavetop.dataone.service.SysUserlogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys_log")
public class SysLogController  {
    @Autowired
    private SysLogService sysLogService;

    @ApiOperation(value = "查询所有操作日志",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "查询操作日志")
    @PostMapping("/findSyslog")
    public   Object findSyslog(Integer current, Integer size) {
        return sysLogService.findAll(current,size);
    }

    @ApiOperation(value = "条件查询操作日志",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "条件查询操作日志")
    @PostMapping("/findLogByCondition")
    public Object findLogByCondition(Long deptId,String operation,String startTime,String endTime,Integer current,Integer size){

        return sysLogService.findLogByCondition(deptId,operation,startTime,endTime,current,size);
    }

}
