package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.SysMonitoring;
import com.cn.wavetop.dataone.service.SysJobinfoService;
import com.cn.wavetop.dataone.service.SysJobrelaService;
import com.cn.wavetop.dataone.service.SysMonitoringService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/toback")
public class ToBackController {

    @Autowired
    private SysJobrelaService sysJobrelaService;

    @Autowired
    private SysJobinfoService sysJobinfoService;
    @Autowired
    private SysMonitoringService sysMonitoringService;
    /**
     * 根据jobid查询数据信息
     * @param jobId
     * @return
     */
    @ApiOperation(value = "后台查询", httpMethod = "GET", protocols = "HTTP", produces = "application/json", notes = "后台查询")
    @GetMapping("/findById")
    public Object findDbinfoById(Long jobId) {

        return sysJobrelaService.findDbinfoById(jobId);
    }

    /**
     * 查询同步数据类型
     * @param Id
     * @return
     */
    @ApiOperation(value = "查看同步方式", httpMethod = "GET", protocols = "HTTP", produces = "application/json", notes = "查看同步方式")
    @GetMapping("/find_range/{Id}")
    public Object findRangeByJobId(@PathVariable long Id) {
        return sysJobrelaService.findRangeByJobId(Id);
    }
    /**
     * 更新读监听数据
     * @param Id
     * @param readData
     */
    @ApiOperation(value = "更新监听数据", httpMethod = "GET", protocols = "HTTP", produces = "application/json", notes = "更新监听数据")
    @GetMapping("/readmonitoring/{Id}")
    public void updateReadMonitoring(@PathVariable long Id, @RequestParam Long readData){
        sysMonitoringService.updateReadMonitoring(Id,readData);
    }


    /**
     * 更新写监听数据
     * @param Id
     * @param writeData
     */
    @ApiOperation(value = "更新监听数据", httpMethod = "GET", protocols = "HTTP", produces = "application/json", notes = "更新监听数据")
    @GetMapping("/writemonitoring/{Id}")
    public void updateWriteMonitoring(@PathVariable long Id,@RequestParam Long writeData){
        sysMonitoringService.updateWriteMonitoring(Id,writeData);
    }
}
