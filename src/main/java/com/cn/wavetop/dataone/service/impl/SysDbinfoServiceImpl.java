package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.SysDbinfoRespository;
import com.cn.wavetop.dataone.dao.SysJobrelaRespository;
import com.cn.wavetop.dataone.entity.SysDbinfo;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.SysDbinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author yongz
 * @Date 2019/10/11、14:34
 */
@Service
public class SysDbinfoServiceImpl implements SysDbinfoService {
    @Autowired
    private SysDbinfoRespository repository;
    @Autowired
    private SysJobrelaRespository sysJobrelarepository;

    @Override
    public Object getDbinfoAll() {
        return ToData.builder().status("1").data(repository.findAll()).build();
    }

    @Override
    public Object getSourceAll() {
        return ToData.builder().status("1").data(repository.findBySourDest(0)).build();
    }

    @Override
    public Object getDestAll() {
        return ToData.builder().status("1").data(repository.findBySourDest(1)).build();
    }


    @Override
    public Object checkDbinfoById(long id) {
        if (repository.existsById(id)) {
            Optional<SysDbinfo> sysDbinfos = repository.findById(id);
            Map<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("data", sysDbinfos);
            return map;
        } else {
            return ToData.builder().status("0").message("任务不存在").build();

        }
    }

    @Transactional
    @Override
    public Object addbinfo(SysDbinfo sysDbinfo) {
        //System.out.println(sysDbinfo);
        //sysDbinfo.getId();
       // if (repository.existsByIdOrName(sysDbinfo.getId(), sysDbinfo.getName())) {
        if (repository.existsByName(sysDbinfo.getName())) {
            return ToData.builder().status("0").message("任务已存在").build();
        } else {
            SysDbinfo data = repository.save(sysDbinfo);
            HashMap<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("data", data);
            return map;
        }
    }

    @Transactional
    @Override
    public Object editDbinfo(SysDbinfo sysDbinfo) {
        Map<Object, Object> map = new HashMap();
        boolean flag = sysJobrelarepository.existsByDestNameOrSourceName(sysDbinfo.getName(), sysDbinfo.getName());
        System.out.println(flag);
        if (!flag) {
            boolean flag2 = repository.existsByName(sysDbinfo.getName());
            System.out.println(flag2);
            if (!flag2) {
                map.put("status", 0);
                map.put("message", "修改失败");
            } else {
                SysDbinfo old = repository.findByIdOrName(sysDbinfo.getId(), sysDbinfo.getName());
                old.setId(sysDbinfo.getId());
                old.setName(sysDbinfo.getName());
                old.setDbname(sysDbinfo.getDbname());
                old.setHost(sysDbinfo.getHost());
                old.setPassword(sysDbinfo.getPassword());
                old.setPort(sysDbinfo.getPort());
                old.setSchema(sysDbinfo.getSchema());
                old.setSourDest(sysDbinfo.getSourDest());
                old.setType(sysDbinfo.getType());
                old.setUser(sysDbinfo.getUser());

                SysDbinfo save = repository.save(old);
                map.put("status", 1);
                map.put("message", "修改成功");
                map.put("data", save);
            }
        } else {
            map.put("status", 2);
            map.put("message", "正在被使用");
        }
        return map;
    }

    @Transactional
    @Override
    public Object deleteDbinfo(long id) {
        Map<Object, Object> map = new HashMap();
        boolean flag = sysJobrelarepository.existsByDestIdOrSourceId(id, id);
        if (!flag) {
            boolean flag2 = repository.existsById(id);
            if (flag2){
                repository.deleteById(id);
                map.put("status", 1);
                map.put("message", "删除成功");
            }else {
                map.put("status", 0);
                map.put("message", "目标不存在");
            }
        } else {
            map.put("status", 2);
            map.put("message", "正在被使用");
        }
        return map;
    }


}
