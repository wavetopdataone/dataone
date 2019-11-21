package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.service.SysJobinfoService;
import com.cn.wavetop.dataone.service.SysJobrelaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toback")
public class ToBackController {

    @Autowired
    private SysJobrelaService sysJobrelaService;

    @Autowired
    private SysJobinfoService sysJobinfoService;
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
}
