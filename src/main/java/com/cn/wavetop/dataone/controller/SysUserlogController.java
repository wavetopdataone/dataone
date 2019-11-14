package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.service.SysUserlogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys_userlog")
public class SysUserlogController  {
    @Autowired
    private SysUserlogService sysUserlogService;

    @ApiOperation(value = "查询所有管理日志",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "查询管理日志")
    @PostMapping("/findSysUserlog")
    public   Object findAllSysUserlog() {
        return sysUserlogService.findAllSysUserlog();
    }

    @ApiOperation(value = "条件查询管理日志",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "条件查询管理日志")
    @PostMapping("/findLog")
    public Object findLog(Long deptId,String operation,String startTime,String endTime){

        return sysUserlogService.findLog(deptId,operation,startTime,endTime);
    }

    /**
     * 导出日志信息
     * @param deptId
     * @param operation
     * @return
     */
//    @ApiOperation(value = "导出管理日志信息", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "导出管理日志信息")
//    @PostMapping("/outUserLog")
//    public Object OutSysUserlogByOperation(@RequestParam Long deptId, @RequestParam String operation, @RequestParam String startTime, @RequestParam String endTime){
//        return sysUserlogService.OutSysUserlogByOperation(deptId,operation,startTime,endTime);
//    }
}
