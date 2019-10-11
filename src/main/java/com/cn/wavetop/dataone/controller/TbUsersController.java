package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.TbUsers;
import com.cn.wavetop.dataone.service.TbUsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class TbUsersController {

    @Autowired
    private TbUsersService tbUsersService;

    @ApiOperation(value = "注册", httpMethod = "POST", protocols = "HTTP", produces = "application/json", notes = "注册用户")
    @PostMapping("/regist")
    public Object regist(TbUsers tbUsers) {
        System.out.println(tbUsers);
        return tbUsersService.addTbUsers(tbUsers);
    }


    @ApiOperation(value = "登录",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "用户登录")
    @PostMapping("/login")
    public Object login(String name,String password){
        System.out.println(name+"----------"+password);
        return tbUsersService.login(name,password);
    }

    @ApiOperation(value = "查询全部用户",httpMethod = "GET",protocols = "HTTP", produces ="application/json", notes = "查询用户")
    @GetMapping("/alluser")
    public Object findAll(){

        return tbUsersService.findAll();
    }
    @ApiOperation(value = "删除用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "删除用户")
    @PostMapping("/delete")
    public Object delete(String name){
        return tbUsersService.delete(name);
    }
}
