package com.cn.wavetop.dataone.config.shiro;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@ControllerAdvice
@ResponseBody
public class ShiroExceptionHandler {

    @ExceptionHandler(value={AuthorizationException.class, UnauthorizedException.class})
    public Object unAuthorizationExceptionHandler(Exception e){
        HashMap<String,String> map=new HashMap<>();
        map.put("stuts","112");
        map.put("message","权限不足");
        return map;
    }

}
