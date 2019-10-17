package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.aop.ServiceLogAspect;
import com.cn.wavetop.dataone.dao.SysDbinfoRespository;
import com.cn.wavetop.dataone.dao.SysJobinfoRespository;
import com.cn.wavetop.dataone.dao.SysJobrelaRespository;
import com.cn.wavetop.dataone.dao.UserlogRespository;
import com.cn.wavetop.dataone.entity.*;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.SysJobrelaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @Author yongz
 * @Date 2019/10/12、10:52
 */
@Service
public class SysJobrelaServiceImpl implements SysJobrelaService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class.getName());

    @Autowired
    private SysJobrelaRespository repository;
    @Autowired
    private SysDbinfoRespository sysDbinfoRespository;
    @Autowired
    private UserlogRespository userlogRespository;
    @Autowired
    private SysJobinfoRespository sysJobinfoRespository;

    @Override
    public Object getJobrelaAll() {
        return ToData.builder().status("1").data(repository.findAll()).build();
    }

    @Override
    public Object checkJobinfoById(long id) {
        {
            if (repository.existsById(id)) {
                Optional<SysJobrela> data = repository.findById(Long.valueOf(id));
                Map<Object, Object> map = new HashMap();
                map.put("status", 1);
                map.put("data", data);
                return map;
            } else {
                return ToData.builder().status("0").message("任务不存在").build();

            }
        }

    }

    @Override
    public Object addJobrela(SysJobrela sysJobrela) {
       // long id = sysJobrela.getId();
        //  SysJobrela sysJobrelabyId = repository.findById(id);
        //SysJobrela sysJobrelabyJobName = repository.findByJobName();
        if (repository.existsByJobName(sysJobrela.getJobName())) {
            return ToData.builder().status("0").message("任务已存在").build();
        } else {
            // 查看端
            SysDbinfo source = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getSourceName(), 0);
            //目标端
            SysDbinfo dest = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getDestName(), 1);

            sysJobrela.setSourceId(source.getId());
            sysJobrela.setSourceType(source.getType());
            sysJobrela.setDestId(dest.getId());
            sysJobrela.setDestType(dest.getType());
            sysJobrela.setJobStatus(Long.valueOf(5));


            SysJobrela save = repository.save(sysJobrela);
            HashMap<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("message", "添加成功");
            map.put("data", save);
            return map;
        }
    }

    @Override
    public Object editJobrela(SysJobrela sysJobrela) {
        HashMap<Object, Object> map = new HashMap();
        long id = sysJobrela.getId();
        // 查看该任务是否存在，存在修改更新任务，不存在新建任务
        if (repository.existsByJobName(sysJobrela.getJobName())) {

            // 查看端
            SysDbinfo source = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getSourceName(), 0);
            //目标端
            SysDbinfo dest = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getDestName(), 1);

            SysJobrela data = repository.findByJobName( sysJobrela.getJobName());
            data.setJobName(sysJobrela.getJobName());
            data.setSourceType(source.getType());
            data.setSourceId(source.getId());
            data.setDestId(dest.getId());
            data.setDestType(dest.getType());
            data.setUserId(sysJobrela.getUserId());
            data.setSyncRange(sysJobrela.getSyncRange());
            data.setSourceName(sysJobrela.getSourceName());
            data.setDestName(sysJobrela.getDestName());
            repository.save(data);

            Userlog build = Userlog.builder().time(new Date()).user("admin").jobName(sysJobrela.getJobName()).operate("修改").build();
            userlogRespository.save(build);

            map.put("status", 1);
            map.put("message", "修改成功");
            map.put("data", data);
        } else {
            map.put("status", 0);
            map.put("message", "修改失败");
        }
        return map;
    }

    @Override
    public Object deleteJobrela(Long id) {
        HashMap<Object, Object> map = new HashMap();
        long id1 = id;
        SysJobrela JobrelabyId = repository.findById(id1);
        if (JobrelabyId != null) {
            long jobStatus = JobrelabyId.getJobStatus();
            if (jobStatus != 1) {
                Userlog build = Userlog.builder().user("admin").jobName(JobrelabyId.getJobName()).time(new Date()).operate("删除").build();
                userlogRespository.save(build);
                repository.deleteById(id);
                sysJobinfoRespository.deleteByJobId(id);
                map.put("status", 1);
                map.put("message", "删除成功");
            } else {
                map.put("status", 0);
                map.put("message", "任务正在进行中");
            }
        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return map;
    }

    @Override
    public Object jobrelaCount() {
        int[] num = new int[6];
        num[0] = repository.countByJobStatus(1); //运行中
        num[1] = repository.countByJobStatus(4);//异常
        num[2] = repository.countByJobStatus(2);//暂停中
        num[3] = repository.countByJobStatus(5);//待完善
        num[4] = repository.countByJobStatus(0);//待激活
        num[5] = repository.countByJobStatus(3);//终止中
        return num;
    }
    @Transactional
    @Override
    public Object queryJobrela(String job_name) {
        HashMap<Object, Object> map = new HashMap();
        List<SysJobrela> data = repository.findByJobNameContaining(job_name);
        if (data != null && data.size() > 0) {
            map.put("status", 1);
            map.put("data", data);
        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return map;
    }

    @Override
    public Object someJobrela(Long job_status) {
        return ToData.builder().status("1").data( repository.findByJobStatus(job_status)).build();
    }
    @Transactional
    @Override
    public Object start(Long id) {
        System.out.println(id);
        HashMap<Object, Object> map = new HashMap();
        long id1 = id;
        SysJobrela byId = repository.findById(id1);
        long jobStatus = byId.getJobStatus();
        if (jobStatus == 0 || jobStatus == 2 || jobStatus == 3) {
            byId.setJobStatus(Long.valueOf(11)); // 1代表运行中，11代表开始动作
            repository.save(byId);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e.getLocalizedMessage());
            }

            Userlog build = Userlog.builder().time(new Date()).user("admin").jobName(byId.getJobName()).operate("启动").jobId(id).build();
            userlogRespository.save(build);

            Userlog build2 = Userlog.builder().time(new Date()).user("admin").jobName(byId.getJobName()).operate("启动成功").jobId(id).build();
            userlogRespository.save(build2);
            List<SysJobrela> sysJobrelas = new ArrayList<>();
            sysJobrelas.add(byId);
            map.put("status", 1);
            map.put("data", sysJobrelas);
            map.put("message", "激活完成");
        } else {
            map.put("status", 0);
            map.put("message", "无法激活");
        }
        return map;
    }
    @Transactional
    @Override
    public Object pause(Long id) {
        System.out.println(id);
        HashMap<Object, Object> map = new HashMap();
        long id1 = id;
        SysJobrela byId = repository.findById(id1);
        long jobStatus = byId.getJobStatus();
        System.out.println(jobStatus);
        if (jobStatus == 1 ) {
            byId.setJobStatus(Long.valueOf(21)); //  2 代表暂停中，21代表暂停动作
            repository.save(byId);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e.getLocalizedMessage());
            }

            Userlog build = Userlog.builder().time(new Date()).user("admin").jobName(byId.getJobName()).operate("暂停").jobId(id).build();
            userlogRespository.save(build);

            Userlog build2 = Userlog.builder().time(new Date()).user("admin").jobName(byId.getJobName()).operate("暂停成功").jobId(id).build();
            userlogRespository.save(build2);
            List<SysJobrela> sysJobrelas = new ArrayList<>();
            sysJobrelas.add(byId);
            map.put("status", 1);
            map.put("data", sysJobrelas);
            map.put("message", "暂停成功");
        } else {
            map.put("status", 0);
            map.put("message", "任务未激活无法暂停");
        }
        return map;
    }
    @Transactional
    @Override
    public Object end(Long id) {
        System.out.println(id);
        HashMap<Object, Object> map = new HashMap();
        long id1 = id;
        SysJobrela byId = repository.findById(id1);
        long jobStatus = byId.getJobStatus();
        if (jobStatus != 1 ) {
            byId.setJobStatus(Long.valueOf(31)); // 3代表终止，31 代表停止功能
            repository.save(byId);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e.getLocalizedMessage());
            }

            Userlog build = Userlog.builder().time(new Date()).user("admin").jobName(byId.getJobName()).operate("停止").jobId(id).build();
            userlogRespository.save(build);

            Userlog build2 = Userlog.builder().time(new Date()).user("admin").jobName(byId.getJobName()).operate("停止成功").jobId(id).build();
            userlogRespository.save(build2);
            List<SysJobrela> sysJobrelas = new ArrayList<>();
            sysJobrelas.add(byId);
            map.put("status", 1);
            map.put("data", sysJobrelas);
            map.put("message", "终止成功");
        } else {
            map.put("status", 0);
            map.put("message", "任务正在运行无法终止");
        }
        return map;
    }
}
