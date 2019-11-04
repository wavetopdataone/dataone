package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.aop.MyLog;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysUserService;
import com.cn.wavetop.dataone.util.PermissionUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;


    @ApiImplicitParam
    @PostMapping("/addUser")
    public Object regist(@RequestBody SysUser sysUser,String id) {

        return sysUserService.addSysUser(sysUser,id);
    }
    @RequiresGuest
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
    @MyLog(value = "删除用户")
    @ApiOperation(value = "删除用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "删除用户")
    @PostMapping("/delete")
    public Object delete(String name){

        return sysUserService.delete(name);
    }
    @MyLog(value = "给用户添加分组")
    @ApiOperation(value = "给用户添加分组",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "给用户添加分组")
    @ApiImplicitParam
    @PostMapping("/updateUser")
    public Object updateUser(Long DeptId,Long id){

        return sysUserService.updateUser(id,DeptId);
    }
    @MyLog(value = "修改用户")
    @ApiOperation(value = "修改用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "修改用户")
    @PostMapping("/update")
    public Object update(@RequestBody SysUser sysUser){

        return sysUserService.update(sysUser);
    }
    @MyLog(value = "退出登录")
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

    @MyLog(value = "冻结用户")
    @ApiOperation(value = "冻结用户",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "冻结用户")
    @PostMapping("/updateStatus")
    public Object updateStatus(Long id ,String status){
        return sysUserService.updateStatus(id,status);
    }

    @ApiOperation(value = "根据用户id查询用户详细信息",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "根据用户id查询用户详细信息")
    @PostMapping("/selSysUser")
    public Object selSysUser(Long userId){

        return sysUserService.selSysUser(userId);
    }
    @MyLog(value = "移交团队")
    @ApiOperation(value = "移交团队id是管理员userid是编辑者",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "移交团队id是管理员userid是编辑者")
    @PostMapping("/HandedTeam")
    public Object HandedTeam(Long id,Long userId ){

        return sysUserService.HandedTeam(id,userId);
    }

    @ApiOperation(value = "全部成员任务名",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "全部成员人物名")
    @PostMapping("/seleUserBystatus")
    public Object seleUserBystatus(String status ){

        return sysUserService.seleUserBystatus(status);
    }
    @ApiOperation(value = "超级管理员移交权限根据管理员id查询出编辑者",httpMethod = "POST",protocols = "HTTP", produces ="application/json", notes = "超级管理员移交权限根据管理员id查询出编辑者")
    @PostMapping("/selectUser")
    public Object selectUserByParentId(Long userId ){
        return sysUserService.selectUserByParentId(userId);
    }

}
