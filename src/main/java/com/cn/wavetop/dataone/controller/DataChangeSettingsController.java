package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.entity.DataChangeSettings;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.DataChangeSettingsService;
import com.cn.wavetop.dataone.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author yongz
 * @Date 2019/10/10、12:56
 * # 数据源变化设置
 */
@Controller
@RequestMapping("/data_change_settings")
public class DataChangeSettingsController {

    @Autowired
    private DataChangeSettingsService service;

    @ApiOperation(value = "查看全部",httpMethod = "GET",protocols = "HTTP",produces = "application/json",notes = "查询用户信息")
    @GetMapping("/data_change_all")
    public ToData data_change_all() {
        return service.getDataChangeSettingsAll();
    }




    @PostMapping("/check_data_change")
    public ToData check_data_change(String job_id) {
        return service.getCheckDataChangeByjobid(job_id);
    }


}
