package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.User;
import com.cn.wavetop.dataone.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @Author yongz
 * @Date 2019/7/7、22:51
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;


    /**
     * 查询所有用户
     *
     * @return
     */
//    @GetMapping
//    public ModelAndView list(Model model){
//        List<User> users = service.getUserByUsername("nijie");
//        model.addAttribute("userList", users);
//        model.addAttribute("title","用户管理");
//        return new ModelAndView("user/list","userModel",model);
//    }

    @GetMapping
    public String list(Model userModel){
        List<User> users = service.getUserByUsername("nijie");
        userModel.addAttribute("userList", users);
        userModel.addAttribute("title","用户管理");
        return "user/list";
    }


    @ApiOperation(value = "根据姓名查用户",httpMethod = "GET",protocols = "HTTP",produces = "application/json",notes = "查询用户信息")
    @ApiImplicitParam(name = "username",value = "姓名",dataType = "String")
    @GetMapping("user")
    public List getInfoByCity(String username) {
        return service.getUserByUsername(username);

    }


    @ApiOperation(value = "根据姓名查用户",httpMethod = "GET",protocols = "HTTP",produces = "application/json",notes = "查询用户信息")
    @ApiImplicitParam(name = "username",value = "姓名",dataType = "String")
    @GetMapping("user2")
    public List getInfoBy(String username) {
        return service.getUserTestByUsername(username);

    }

}
