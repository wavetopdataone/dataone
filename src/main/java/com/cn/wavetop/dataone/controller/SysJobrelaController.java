package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.SysJobrela;
import com.cn.wavetop.dataone.service.SysJobinfoService;
import com.cn.wavetop.dataone.service.SysJobrelaService;
import com.cn.wavetop.dataone.service.SysUserJobrelaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author yongz
 * @Date 2019/10/10、11:45
 */
@RestController
@RequestMapping("/sys_jobrela")
public class SysJobrelaController {

    @Autowired
    private SysJobrelaService service;
    @Autowired
    private SysUserJobrelaService sysUserJobrelaService;

    @ApiOperation(value = "根据用户权限不同显示不同任务", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "查询用户信息")
    @PostMapping("/jobrela_all")
    public Object jobrela_all(Integer current,Integer size) {
        return service.getJobrelaAll( current, size);
    }

    @ApiImplicitParam
    @PostMapping("/check_jobinfo")
    public Object check_jobinfo(long id) {
        return service.checkJobinfoById(id);
    }

    @ApiOperation(value = "添加任务添加参与人", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "添加任务添加参与人")
    @PostMapping("/add_jobrela")
    public Object add_jobrela(@RequestBody  SysJobrela sysJobrela,@RequestParam(required = false) String names) {
        return service.addJobrela(sysJobrela,names);
    }

    @ApiImplicitParam
    @PostMapping("/edit_jobrela")
    public Object edit_jobrela(@RequestBody SysJobrela sysJobrela) {
        System.out.println(sysJobrela);
        return service.editJobrela(sysJobrela);
    }
    @ApiImplicitParam
    @PostMapping("/delete_jobrela")
    public Object delete_jobrela(Long id) {
        System.out.println(id);
        return service.deleteJobrela(id);
    }

    // 查询个任务状态接口
    @ApiImplicitParam
    @PostMapping("/jobrela_count")
    public Object jobrela_count() {

        return service.jobrelaCount();
    }

    //模糊查询
    @ApiImplicitParam
    @PostMapping("/query_jobrela")
    public Object query_jobrela(String job_name,Integer current,Integer size ) {

        return service.queryJobrela(job_name,current,size);
    }

    //
    @ApiImplicitParam
    @PostMapping("/some_jobrela")
    public Object some_jobrela(String job_status,Integer current,Integer size) {
        return service.someJobrela(job_status,current,size);
    }

    @ApiImplicitParam
    @PostMapping("/start")
    public Object start(Long id) {
        return service.start(id);
    }


    @ApiImplicitParam
    @PostMapping("/pause")
    public Object pause(Long id) {
        return service.pause(id);
    }

    @ApiImplicitParam
    @PostMapping("/end")
    public Object end(Long id) {
        return service.end(id);
    }

    @ApiOperation(value = "管理员编辑者查看任务", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "管理员编辑者查看任务")
    @PostMapping("/sel_jobrela")
    public Object selJobrela(Integer current,Integer size) {
        return service.selJobrela(current,size);
    }

    @ApiOperation(value = "为用户选任务", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "为用户选任务")
    @PostMapping("/addUserJob")
    public Object addUserJobRela(Long userId,String jobrela_id) {
        return sysUserJobrelaService.addUserJobRela(userId,jobrela_id);
    }

}
