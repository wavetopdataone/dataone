package com.cn.wavetop.dataone.controller;

import com.cn.wavetop.dataone.config.SpringContextUtil;
import com.cn.wavetop.dataone.dao.*;
import com.cn.wavetop.dataone.entity.SysMonitoring;

public class MonitoringClient extends Thread {

    private SysJobrelaRespository repository = (SysJobrelaRespository) SpringContextUtil.getBean("sysJobrelaRespository");
    private SysMonitoringRepository sysMonitoringRepository = (SysMonitoringRepository) SpringContextUtil.getBean("sysMonitoringRepository");

    private boolean stopMe = true;
    public void stopMe() {
        this.stopMe = false;
    }
    @Override
    public void run(){


    }
}
