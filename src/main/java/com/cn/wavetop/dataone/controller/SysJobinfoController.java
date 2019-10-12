package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.SysGroup;
import com.cn.wavetop.dataone.entity.SysJobinfo;
import com.cn.wavetop.dataone.service.SysGroupService;
import com.cn.wavetop.dataone.service.SysJobinfoService;
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
@RequestMapping("/sys_jobinfo")
public class SysJobinfoController {

    @Autowired
    private SysJobinfoService service;

    @ApiOperation(value = "查看全部", httpMethod = "GET", protocols = "HTTP", produces = "application/json", notes = "查询用户信息")
    @GetMapping("/jobinfo_all")
    public Object jobinfo_all() {
        return service.getJobinfoAll();
    }

    @ApiImplicitParam
    @PostMapping("/check_jobinfo")
    public Object check_jobinfo(long job_id) {

        return service.checkJobinfoByJobId(job_id);
    }

    @ApiImplicitParam
    @PostMapping("/add_jobinfo")
    public Object add_jobinfo( SysJobinfo jobinfo) {
        return service.addJobinfo(jobinfo);
    }

    @ApiImplicitParam
    @PostMapping("/edit_jobinfo")
    public Object edit_jobinfo(SysJobinfo jobinfo) {
        System.out.println(jobinfo);
        return service.editJobinfo(jobinfo);
    }
    @ApiImplicitParam
    @PostMapping("/delete_jobinfo")
    public Object delete_jobinfo(Long job_id) {
        System.out.println(job_id);
        return service.deleteJobinfo(job_id);
    }


}
