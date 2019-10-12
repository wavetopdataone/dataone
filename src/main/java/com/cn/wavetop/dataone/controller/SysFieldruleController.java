package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.SysDbinfo;
import com.cn.wavetop.dataone.entity.SysFieldrule;
import com.cn.wavetop.dataone.service.SysDbinfoService;
import com.cn.wavetop.dataone.service.SysFieldruleService;
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
@RequestMapping("/sys_fieldrule")
public class SysFieldruleController {

    @Autowired
    private SysFieldruleService service;

    @ApiOperation(value = "查看全部", httpMethod = "GET", protocols = "HTTP", produces = "application/json", notes = "查询用户信息")
    @GetMapping("/fieldrule_all")
    public Object fieldrule_all() {
        return service.getFieldruleAll();
    }

    @ApiImplicitParam
    @PostMapping("/check_fieldrule")
    public Object check_fieldrule(long job_id) {
        return service.checkFieldruleByJobId(job_id);
    }

    @ApiImplicitParam
    @PostMapping("/add_fieldrule")
    public Object add_fieldrule(SysFieldrule sysFieldrule, String list_data) {
        System.out.println(sysFieldrule + list_data);
        return service.addFieldrule(sysFieldrule);
    }

    @ApiImplicitParam
    @PostMapping("/edit_fieldrule")
    public Object edit_fieldrule(String list_data, String source_name, String dest_name, Long job_id) {
        return service.editFieldrule(list_data, source_name, dest_name, job_id);
    }

    @ApiImplicitParam
    @PostMapping("/delete_fieldrule")
    public Object delete_fieldrule(String source_name) {
        return service.deleteFieldrule(source_name);
    }

    @ApiImplicitParam
    @PostMapping("/link_table_details")
    public Object link_table_details(SysDbinfo sysDbinfo ) {
        return service.linkTableDetails(sysDbinfo);
    }


}
