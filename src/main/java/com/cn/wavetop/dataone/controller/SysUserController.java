package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.TbUsers;
import com.cn.wavetop.dataone.service.SysUserService;
import com.cn.wavetop.dataone.service.TbUsersService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;


    @ApiOperation(value = "添加", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "添加用户")
    @PostMapping("/addUser")
    public Object regist(@RequestBody SysUser sysUser) {
        return sysUserService.addSysUser(sysUser);
    }


    @ApiOperation(value = "登录",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "用户登录")
    @PostMapping("/login")
    public Object login(String name,String password){
        System.out.println(name+"----------"+password);
        return sysUserService.login(name,password);
    }

    @ApiOperation(value = "查询全部用户",httpMethod = "GET",protocols = "HTTP", produces ="application/json", notes = "查询用户")
    @GetMapping("/alluser")
    public Object findAll(){
        return sysUserService.findAll();
    }
    @ApiOperation(value = "删除用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "删除用户")
    @PostMapping("/delete")
    public Object delete(String name){
        return sysUserService.delete(name);
    }
}
