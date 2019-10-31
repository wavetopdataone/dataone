package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public Object login(){
        return ToDataMessage.builder().status("800").message("用户为登录").build();
    }
}
