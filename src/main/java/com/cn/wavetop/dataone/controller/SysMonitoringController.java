package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.SysMonitoring;
import com.cn.wavetop.dataone.entity.SysRela;
import com.cn.wavetop.dataone.service.SysMonitoringService;
import com.cn.wavetop.dataone.service.SysRelaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys_monitoring")
public class SysMonitoringController {
    @Autowired
    private SysMonitoringService sysMonitoringService;

    @ApiOperation(value = "查看全部", protocols = "HTTP", produces = "application/json", notes = "查看全部")
    @RequestMapping("/monitoring_all")
    public Object userAll(){
        return sysMonitoringService.findAll();
    }
    @ApiOperation(value = "单一查询", protocols = "HTTP", produces = "application/json", notes = "单一查询")
    @RequestMapping("/check_monitoring")
    public Object checkUser(long job_id){
        return sysMonitoringService.findByJobId(job_id);
    }
    @ApiOperation(value = "添加", protocols = "HTTP", produces = "application/json", notes = "添加")
    @RequestMapping("/add_monitoring")
    public Object addUser(@RequestBody SysMonitoring sysMonitoring){
        return sysMonitoringService.addSysMonitoring(sysMonitoring);
    }
    @ApiOperation(value = "修改", protocols = "HTTP", produces = "application/json", notes = "修改")
    @RequestMapping("/edit_monitoring")
    public Object editUser(@RequestBody SysMonitoring sysMonitoring){
        return sysMonitoringService.update(sysMonitoring);
    }
    @ApiOperation(value = "删除", protocols = "HTTP", produces = "application/json", notes = "删除")
    @RequestMapping("/delete_monitoring")
    public Object deleteUser(long job_id){
        return sysMonitoringService.delete(job_id);
    }

    @ApiOperation(value = "模糊查询", protocols = "HTTP", produces = "application/json", notes = "模糊查询")
    @RequestMapping("/query_monitoring")
    public Object queryMonitoring(String source_table,long job_id){
        return sysMonitoringService.findLike(source_table,job_id);
    }
    @ApiOperation(value = "根据jobid查询条数和时间", protocols = "HTTP", produces = "application/json", notes = "根据jobid查询条数和时间")
    @RequestMapping("/data_rate")
    public Object dataRate(long job_id){
        return sysMonitoringService.dataRate(job_id);
    }
    @ApiOperation(value = "根据jobId查询读取写入错误行", protocols = "HTTP", produces = "application/json", notes = "根据jobId查询读取写入错误行")
    @RequestMapping("/show_monitoring")
    public Object showMonitoring(long job_id){
        return sysMonitoringService.showMonitoring(job_id);
    }
    @ApiOperation(value = "插入目标表名,显示该记录", protocols = "HTTP", produces = "application/json", notes = "插入目标表名,显示该记录")
    @RequestMapping("/table_monitoring")
    public Object tableMonitoring(long job_id){
        return sysMonitoringService.tableMonitoring(job_id);
    }
}
