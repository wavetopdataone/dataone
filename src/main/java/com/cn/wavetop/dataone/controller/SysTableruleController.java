package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.SysTablerule;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.service.SysTableruleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/sys_tablerule")
public class SysTableruleController {
    @Autowired
    private SysTableruleService sysTableruleService;

    @ApiOperation(value = "查看全部", protocols = "HTTP", produces = "application/json", notes = "查看全部")
    @RequestMapping("/tablerule_all")
    public Object tableruleAll(){
        return sysTableruleService.tableruleAll();
    }
    @ApiOperation(value = "单一查询", protocols = "HTTP", produces = "application/json", notes = "单一查询")
    @RequestMapping("/check_tablerule")
    public Object checkTablerule(long job_id){
        return sysTableruleService.checkTablerule(job_id);
    }
    @ApiOperation(value = "添加", protocols = "HTTP", produces = "application/json", notes = "添加")
    @RequestMapping("/add_tablerule")
    public Object addTablerule(SysTablerule sysTablerule)
    {
        return sysTableruleService.addTablerule(sysTablerule);
    }
    @ApiOperation(value = "修改", protocols = "HTTP", produces = "application/json", notes = "修改")
    @RequestMapping("/edit_tablerule")
    public Object editTablerule(SysTablerule sysTablerule){

        return sysTableruleService.editTablerule(sysTablerule);
    }
    @ApiOperation(value = "删除", protocols = "HTTP", produces = "application/json", notes = "删除")
    @RequestMapping("/delete_tablerule")
    public Object deleteTablerule(long job_id){

        return sysTableruleService.deleteTablerule(job_id);
    }
    @ApiOperation(value = "连接数据库查询", protocols = "HTTP", produces = "application/json", notes = "连接数据库查询")
    @RequestMapping("/link_data_table")
    public Object linkDataTable(String type,String host,String user,String port,String password,String dbname,String schema) throws SQLException {

        return sysTableruleService.linkDataTable( type, host, user, port, password, dbname, schema);
    }

}
