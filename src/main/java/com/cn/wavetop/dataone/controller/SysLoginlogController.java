package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.aop.MyLog;
import com.cn.wavetop.dataone.service.SysLoginlogSerivece;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Object loginlogDept(@RequestParam Integer current, @RequestParam Integer size) {
        return sysLoginlogSerivece.loginlogDept(current,size);

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
    public Object findSysUserlogByOperation(@RequestParam Long deptId,@RequestParam String operation,@RequestParam String startTime,@RequestParam String endTime,@RequestParam Integer current,@RequestParam Integer size){
        return sysLoginlogSerivece.findSysLoginlogByOperation(deptId,operation,startTime,endTime,current,size);
    }

}
