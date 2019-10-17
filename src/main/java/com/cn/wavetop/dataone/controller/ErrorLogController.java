package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.ErrorLog;
import com.cn.wavetop.dataone.entity.ErrorQueueSettings;
import com.cn.wavetop.dataone.service.ErrorLogService;
import com.cn.wavetop.dataone.service.ErrorQueueSettingsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author yongz
 * @Date 2019/10/10、11:45
 */
@RestController
@RequestMapping("/errorlog")
public class ErrorLogController {

    @Autowired
    private ErrorLogService service;

    @ApiOperation(value = "查看全部", httpMethod = "GET", protocols = "HTTP", produces = "application/json", notes = "查询用户信息")
    @GetMapping("/errorlog_all")
    public Object errorlog_all() {
        return service.getErrorlogAll();
    }

    @ApiImplicitParam(name = "id", value = "id", dataType = "long")
    @PostMapping("/check_errorlog")
    public Object check_errorlog(long Id) {
        return service.getCheckErrorLogById(Id);
    }

    @ApiImplicitParam
    @PostMapping("/add_errorlog")
    public Object add_errorlog( @RequestBody ErrorLog errorLog) {
        System.out.println(errorLog);
        return service.addErrorlog(errorLog);
    }

    @ApiImplicitParam
    @PostMapping("/edit_errorlog")
    public Object edit_errorlog(@RequestBody ErrorLog ErrorLog) {
        System.out.println(ErrorLog);
        return service.editErrorlog(ErrorLog);
    }

    @ApiImplicitParam(name = "id", value = "id", dataType = "long")
    @PostMapping("/delete_errorlog")
    public Object delete_errorlog(long id) {
        System.out.println(id);
        return service.deleteErrorlog(id);
    }

    @PostMapping("/query_errorlog")
    public Object query_errorlog(String job_name) {
        System.out.println(job_name);
        return service.queryErrorlog(job_name);
    }
}
