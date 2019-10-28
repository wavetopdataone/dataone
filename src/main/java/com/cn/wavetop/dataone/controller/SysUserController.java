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
    public Object regist(@RequestBody SysUser sysUser,Long id) {

        return sysUserService.addSysUser(sysUser,id);
    }


    @ApiOperation(value = "登录",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "用户登录")
    @PostMapping("/login")
    public Object login(String name,String password){

        return sysUserService.login(name,password);
    }

    @ApiOperation(value = "根据用户权限查询用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "查询用户")
    @PostMapping("/alluser")
    public Object findAll(){
        return sysUserService.findAll();
    }
    @ApiOperation(value = "删除用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "删除用户")
    @PostMapping("/delete")
    public Object delete(String name){

        return sysUserService.delete(name);
    }
    @ApiOperation(value = "给用户添加分组",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "给用户添加分组")
    @PostMapping("/updateUser")
    public Object updateUser(Long id,Long DeptId){

        return sysUserService.updateUser(id,DeptId);
    }
    @ApiOperation(value = "修改用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "修改用户")
    @PostMapping("/update")
    public Object update(@RequestBody SysUser sysUser){

        return sysUserService.update(sysUser);
    }

    @ApiOperation(value = "退出登录",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "退出登录")
    @PostMapping("/login_out")
    public Object loginOut(){
        return sysUserService.loginOut();
    }
    @ApiOperation(value = "根据用户名查找角色权限",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "根据用户名查找角色权限")
    @PostMapping("/findRolePerms")
    public Object findRolePerms(String loginName){
        return sysUserService.findRolePerms(loginName);
    }
    //跟findUserByDept借口效果差不多？这个不适用与超级管理员
//    @ApiOperation(value = "查询管理员组下的全部人员",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "查询管理员组下的全部人员")
//    @PostMapping("/findAllUser")
//    public Object findAllUser(){
//
//        return sysUserService.findById();
//    }


    @ApiOperation(value = "用户模糊查询",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "用户模糊查询")
    @PostMapping("/findByName")
    public Object findByUserName(String userName){

        return sysUserService.findByUserName(userName);
    }
    @ApiOperation(value = "根据部门ID查询该部门下的用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "根据部门ID查询该部门下的用户")
    @PostMapping("/findUserByDept")
    public Object findUserByDept(Long deptId){
        return sysUserService.findUserByDept(deptId);
    }

    @ApiOperation(value = "冻结用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "冻结用户")
    @PostMapping("/updateStatus")
    public Object updateStatus(Long id ,String status){
        return sysUserService.updateStatus(id,status);
    }

}
