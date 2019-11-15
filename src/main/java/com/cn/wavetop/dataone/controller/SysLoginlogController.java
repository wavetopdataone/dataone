package com.cn.wavetop.dataone.controller;


import com.cn.wavetop.dataone.entity.SysLoginlog;
import com.cn.wavetop.dataone.service.SysLoginlogSerivece;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户登录日志详情
 * @author wang
 */
@RestController
@RequestMapping("/sys_loginlog")
public class SysLoginlogController {

    @Autowired
    private SysLoginlogSerivece sysLoginlogSerivece;

    /**超管或者管理员进来查看日志
     *
     */
    @ApiOperation(value = "全部查询登录日志", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "初始数据")
    @PostMapping("/loginlog_dept")
    public Object loginlogDept() {
        return sysLoginlogSerivece.loginlogDept();

    }

    /**
     * 根据操作条件查询登录日志
     * @param deptId
     * @param operation
     * @param current
     * @param size
     * @return
     */
    @ApiOperation(value = "根据操作对象查询登录日志", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "根据操作对象查询登录日志")
    @PostMapping("/loginlog_operation")
    public Object findSysUserlogByOperation(@RequestParam Long deptId,@RequestParam Long userId,@RequestParam String operation,@RequestParam String startTime,@RequestParam String endTime){
        return sysLoginlogSerivece.findSysLoginlogByOperation(deptId,userId,operation,startTime,endTime);
    }

    /**
     * 导出日志信息
     * @param deptId
     * @param operation
     * @return
     */
//    @ApiOperation(value = "导出日志信息", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "导出日志信息")
//    @PostMapping("/outLoginlog")
//    public Object OutSysLoginlogByOperation(@RequestParam Long deptId,@RequestParam String operation,@RequestParam String startTime,@RequestParam String endTime){
//        return sysLoginlogSerivece.OutSysLoginlogByOperation(deptId,operation,startTime,endTime);
//    }

}
