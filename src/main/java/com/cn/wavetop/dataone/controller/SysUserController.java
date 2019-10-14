package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "查看全部", protocols = "HTTP", produces = "application/json", notes = "查看全部")
    @RequestMapping("/user_all")
    public Object userAll(){
        return sysUserService.findAll();
    }
    @ApiOperation(value = "单一查询", protocols = "HTTP", produces = "application/json", notes = "单一查询")
    @RequestMapping("/check_user")
    public Object checkUser(long id){
        return sysUserService.findById(id);
    }
    @ApiOperation(value = "添加", protocols = "HTTP", produces = "application/json", notes = "添加")
    @RequestMapping("/add_user")
    public Object addUser(@RequestBody SysUser sysUser){
        return sysUserService.addSysUser(sysUser);
    }
    @ApiOperation(value = "修改", protocols = "HTTP", produces = "application/json", notes = "修改")
    @RequestMapping("/edit_user")
    public Object editUser(@RequestBody SysUser sysUser){
        return sysUserService.update(sysUser);
    }
    @ApiOperation(value = "删除", protocols = "HTTP", produces = "application/json", notes = "删除")
    @ApiImplicitParam(name = "id", value = "id", dataType = "long")
    @RequestMapping("/delete_user")
    public Object deleteUser(long id){
        return sysUserService.delete(id);
    }

}
