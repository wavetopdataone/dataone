package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.ErrorLog;
import com.cn.wavetop.dataone.entity.ErrorQueueSettings;
import com.cn.wavetop.dataone.service.ErrorLogService;
import com.cn.wavetop.dataone.service.ErrorQueueSettingsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yongz
 * @Date 2019/10/10、11:45
 */
@RestController
@RequestMapping("/error_queue_settings")
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
    public Object add_errorlog(ErrorLog errorLog) {
        System.out.println(errorLog);
        return service.addErrorlog(errorLog);
    }

    @ApiImplicitParam
    @PostMapping("/edit_errorlog")
    public Object edit_errorlog(ErrorLog ErrorLog) {
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
