package com.cn.wavetop.dataone;

import com.cn.wavetop.dataone.config.SpringContextUtil;
import com.cn.wavetop.dataone.controller.EmailClient;
import com.cn.wavetop.dataone.controller.MonitoringClient;
import com.cn.wavetop.dataone.service.SysJobrelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataoneApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DataoneApplication.class, args);
        new SpringContextUtil().setApplicationContext(context);


        new EmailClient().start();
        new MonitoringClient().start();
    }



//    @Bean
//    public SysJobrelaService sysJobrelaService(){
//        return  this.sysJobrelaService;
//    };
    

}
