package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.aop.ServiceLogAspect;
import com.cn.wavetop.dataone.dao.*;
import com.cn.wavetop.dataone.entity.*;
import com.cn.wavetop.dataone.entity.vo.SysJobrelaUser;
import com.cn.wavetop.dataone.entity.vo.SysUserJobVo;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysJobrelaService;
import com.cn.wavetop.dataone.util.LogUtil;
import com.cn.wavetop.dataone.util.PermissionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    @Autowired
    private SysUserJobrelaRepository sysUserJobrelaRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysLogRepository sysLogRepository;
    @Autowired
    private SysJobrelaRelatedRespository sysJobrelaRelatedRespository;
    @Autowired
    private DataChangeSettingsRespository dataChangeSettingsRespository;
    @Autowired
    private SysTableruleRepository sysTableruleRepository;
    @Autowired
    private SysFilterTableRepository sysFilterTableRepository;
    @Autowired
    private SysFieldruleRepository sysFieldruleRepository;
    @Autowired
    private SysJorelaUserextraRepository sysJorelaUserextraRepository;
    @Autowired
    private  LogUtil logUtil;

    @Override
    public Object getJobrelaAll(Integer current, Integer size) {
        Map<Object, Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(current - 1, size, Sort.Direction.DESC, "id");
        if (PermissionUtils.isPermitted("1")) {
            Page<SysJobrela> list = repository.findAll(pageable);
            map.put("status", "1");
            map.put("totalCount", list.getTotalElements());
            map.put("data", list.getContent());

        } else {
            if(PermissionUtils.getSysUser()==null){
                return ToDataMessage.builder().status("401").message("请先登录").build();
            }
            List<SysJobrela> list = repository.findByUserId(PermissionUtils.getSysUser().getId(), pageable);
            List<SysJobrela> list2 = repository.findByUserId(PermissionUtils.getSysUser().getId());
            map.put("status", "1");
            map.put("totalCount", list2.size());
            map.put("data", list);

        }
        return map;
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

    @Transactional
    @Override
    public Object addJobrela(SysJobrela sysJobrela) {
        // long id = sysJobrela.getId();
        //  SysJobrela sysJobrelabyId = repository.findById(id);
        //SysJobrela sysJobrelabyJobName = repository.findByJobName();
        HashMap<Object, Object> map = new HashMap();
        List<SysUser> sysUserList=new ArrayList<>();
        SysUserJobrela sysUserJobrela=null;
        SysJobrela save=null;
        SysJobrela sysJobrela1=null;
        List<Long> jobIds=new ArrayList<>();
        if (PermissionUtils.isPermitted("2")) {
            if (repository.existsByJobName(sysJobrela.getJobName())) {
                return ToData.builder().status("0").message("任务已存在").build();
            } else {
                String[] name = sysJobrela.getDestName().split(",");
                String jobName=sysJobrela.getJobName();
                 for(int i=0;i<name.length;i++) {
                     sysJobrela1=new SysJobrela();
                     // 查看端
                     SysDbinfo source = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getSourceName(), 0);
                     //目标端
                     SysDbinfo dest = sysDbinfoRespository.findByNameAndSourDest(name[i], 1);
                     if(i==0){
                         sysJobrela1.setJobName(jobName);
                     }else{
                         jobName=null;
                         jobName=sysJobrela.getJobName()+"_"+i;
                         sysJobrela1.setJobName(jobName);
                     }
                     sysJobrela1.setSourceId(source.getId());
                     sysJobrela1.setSourceType(source.getType());
                     sysJobrela1.setSourceName(source.getName());

                     //第一个主任务目的端先用，拼接上，等配置完成后改回来
                     if(i==0) {
                         sysJobrela1.setDestName(sysJobrela.getDestName());
                     }else{
                         sysJobrela1.setDestName(name[i]);
                     }
                     sysJobrela1.setDestId(dest.getId());
                     sysJobrela1.setDestType(dest.getType());
                     sysJobrela1.setJobStatus("5");
                      save = repository.save(sysJobrela1);
                     jobIds.add(save.getId());
                     //主任务添加到用户任务中，子任务在配置完成后添加
                     if(jobName.equals(sysJobrela.getJobName())) {
                         sysUserJobrela = new SysUserJobrela();
                         sysUserJobrela.setUserId(PermissionUtils.getSysUser().getId());
                         sysUserJobrela.setDeptId(PermissionUtils.getSysUser().getDeptId());
                         sysUserJobrela.setJobrelaId(save.getId());
                         sysUserJobrelaRepository.save(sysUserJobrela);
                     }

                     Userlog build = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(jobName).operate("添加").jobId(save.getId()).build();
                     userlogRespository.save(build);
                     SysJobrela s  =repository.findByJobName(jobName);
                     //添加任务日志
                     logUtil.addJoblog(s,"com.cn.wavetop.dataone.service.impl.SysJobrelaServiceImpl.addJobrela","添加任务");

                 }
                 SysJobrelaRelated sysJobrelaRelated=null;
                 Long jobId=jobIds.get(0);
                 //给主任务一个表示
//                Optional<SysJobrela> sysJobrela2=repository.findById(jobId);
//                sysJobrela2.get().setRelevence("1");
//                repository.save(sysJobrela2.get());
                jobIds.remove(0);
                //添加与主任务对应的子任务
                 for(Long id:jobIds){
                     //给子任务一个表示
//                     Optional<SysJobrela> sysJobrela3=repository.findById(id);
//                     sysJobrela3.get().setRelevence("0");
//                     repository.save(sysJobrela3.get());
                     sysJobrelaRelated=new SysJobrelaRelated();
                     sysJobrelaRelated.setMasterJobId(jobId);
                     sysJobrelaRelated.setSlaveJobId(id);
                     sysJobrelaRelatedRespository.save(sysJobrelaRelated);
                 }

                Optional<SysJobrela> ss=repository.findById(jobId);
                map.put("status", 1);
                map.put("message", "添加成功");
                map.put("data", ss.get());
            }
        } else {
            map.put("status", "2");
            map.put("message", "权限不足");
        }
        return map;
    }

    @Transactional
    @Override
    public Object editJobrela(SysJobrela sysJobrela) {
        HashMap<Object, Object> map = new HashMap();
        SysJobrela data=null;

        if (PermissionUtils.isPermitted("2")||PermissionUtils.isPermitted("3")) {
            long id = sysJobrela.getId();
            // 查看该任务是新建否存在，存在修改更新任务，不存在任务
            if (repository.existsByJobName(sysJobrela.getJobName())) {

                //没有配置完成并且是主任务修改的话直接把子任务删除
//                List<SysJobrelaRelated> sysJobrelaRelateds= sysJobrelaRelatedRespository.findByMasterJobId(sysJobrela.getId());
//                if(sysJobrelaRelateds!=null&&sysJobrelaRelateds.size()>0) {
//                    for (SysJobrelaRelated sysJobrelaRelated : sysJobrelaRelateds) {
//                        repository.deleteById(sysJobrelaRelated.getSlaveJobId());
//                        sysJobinfoRespository.deleteByJobId(sysJobrelaRelated.getSlaveJobId());
//                        sysUserJobrelaRepository.deleteByJobrelaId(sysJobrelaRelated.getSlaveJobId());
//                        dataChangeSettingsRespository.deleteByJobId(sysJobrelaRelated.getSlaveJobId());
//                        sysTableruleRepository.deleteByJobId(sysJobrelaRelated.getSlaveJobId());
//                        sysFieldruleRepository.deleteByJobId(sysJobrelaRelated.getSlaveJobId());
//                        sysFilterTableRepository.deleteByJobId(sysJobrelaRelated.getSlaveJobId());
//                        if(sysJobrelaRelatedRespository.existsBySlaveJobId(sysJobrelaRelated.getSlaveJobId())) {
//                            sysJobrelaRelatedRespository.deleteBySlaveJobId(sysJobrelaRelated.getSlaveJobId());
//                        }
//                    }
//                }


//                String[] name = sysJobrela.getDestName().split(",");
//                String jobName=sysJobrela.getJobName();
               data = repository.findByJobName(sysJobrela.getJobName());
//                for(int i=0;i<name.length;i++) {

                    // 查看端
                    SysDbinfo source = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getSourceName(), 0);
                    //目标端
                    SysDbinfo dest = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getDestName(), 1);
                    data.setJobName(sysJobrela.getJobName());
                    data.setSourceType(source.getType());
                    data.setSourceId(source.getId());
                    data.setDestId(dest.getId());
                    data.setDestType(dest.getType());
                    data.setUserId(sysJobrela.getUserId());
                  //  data.setSyncRange(sysJobrela.getSyncRange());
                    data.setSourceName(sysJobrela.getSourceName());
                    data.setDestName(sysJobrela.getDestName());
                    repository.save(data);

                    Userlog build = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(sysJobrela.getJobName()).operate("修改").jobId(data.getId()).build();
                    userlogRespository.save(build);
//                }
                    //添加任务日志
                    logUtil.addJoblog(data, "com.cn.wavetop.dataone.service.impl.editJobrela", "修改任务");


                map.put("status", 1);
                map.put("message", "修改成功");
                map.put("data", data);
            } else {
                map.put("status", 0);
                map.put("message", "任务不存在");
            }
        } else {
            map.put("status", "2");
            map.put("message", "权限不足");
        }
        return map;
    }

    @Transactional
    @Override
    public Object deleteJobrela(Long id) {
        HashMap<Object, Object> map = new HashMap();
        long id1 = id;
        SysJobrela JobrelabyId = repository.findById(id1);
        if (PermissionUtils.isPermitted("2")) {
            if (JobrelabyId != null) {
                String jobStatus = JobrelabyId.getJobStatus();
                if (!"1".equals(jobStatus)) {
                    Userlog build = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(JobrelabyId.getJobName()).operate("删除").jobId(JobrelabyId.getId()).build();
                    userlogRespository.save(build);
                    repository.deleteById(id);
                    sysJobinfoRespository.deleteByJobId(id);
                    sysUserJobrelaRepository.deleteByJobrelaId(id);
                    dataChangeSettingsRespository.deleteByJobId(id);
                    sysTableruleRepository.deleteByJobId(id);
                    sysFieldruleRepository.deleteByJobId(id);
                    sysFilterTableRepository.deleteByJobId(id);
                    if(sysJobrelaRelatedRespository.existsBySlaveJobId(id)) {
                        sysJobrelaRelatedRespository.deleteBySlaveJobId(id);
                    }
                    List<SysJobrelaRelated> sysJobrelaRelateds = sysJobrelaRelatedRespository.findByMasterJobId(id);
                    if (sysJobrelaRelateds != null && sysJobrelaRelateds.size() > 0) {
                        sysJobrelaRelatedRespository.delete(id);

                        for (SysJobrelaRelated sysJobrelaRelated : sysJobrelaRelateds) {

                            repository.deleteById(sysJobrelaRelated.getSlaveJobId());
                            sysJobinfoRespository.deleteByJobId(sysJobrelaRelated.getSlaveJobId());
                            sysUserJobrelaRepository.deleteByJobrelaId(sysJobrelaRelated.getSlaveJobId());
                            dataChangeSettingsRespository.deleteByJobId(id);
                            sysTableruleRepository.deleteByJobId(id);
                            sysFieldruleRepository.deleteByJobId(id);
                            sysFilterTableRepository.deleteByJobId(id);
                        }
                    }
                    map.put("status", 1);
                    map.put("message", "删除成功");
                    //添加任务日志
                   logUtil.addJoblog(JobrelabyId,"com.cn.wavetop.dataone.service.impl.deleteJobrela","删除任务");
                } else {
                    map.put("status", 0);
                    map.put("message", "任务正在进行中");
                }
            } else {
                map.put("status", 0);
                map.put("message", "任务不存在");
            }
        } else {
            map.put("status", "2");
            map.put("message", "权限不足");
        }
        return map;
    }

    @Override
    public Object jobrelaCount(Long deptId) {
        int[] num = new int[6];
        if(PermissionUtils.getSysUser()==null) {
            return ToDataMessage.builder().status("401").message("请先登录").build();
        }
        Long id  = PermissionUtils.getSysUser().getId();//登录用户的id
        if(PermissionUtils.isPermitted("1")){
            num[0] = repository.countByJobStatusLike(1 + "%"); //运行中
            num[1] = repository.countByJobStatusLike(4 + "%");//异常
            num[2] = repository.countByJobStatusLike(2 + "%");//暂停中
            num[3] = repository.countByJobStatusLike(5 + "%");//待完善
            num[4] = repository.countByJobStatusLike(0 + "%");//待激活
            num[5] = repository.countByJobStatusLike(3 + "%");//终止中
            if(deptId!=0){
              SysUser s= sysUserRepository.findUserByDeptId(deptId);
              //如果s等于null说明该部门下没有管理员，那么该部门下也没有任务，状态也应该都为0
              if(s!=null){
                  num[0] = repository.countByJobStatus(s.getId(),1 + "%"); //运行中
                  num[1] = repository.countByJobStatus(s.getId(),4 + "%");//异常
                  num[2] = repository.countByJobStatus(s.getId(),2 + "%");//暂停中
                  num[3] = repository.countByJobStatus(s.getId(),5 + "%");//待完善
                  num[4] = repository.countByJobStatus(s.getId(),0 + "%");//待激活
                  num[5] = repository.countByJobStatus(s.getId(),3 + "%");//终止中
              }else{
                  num[0]=0;num[1]=0;num[2]=0;num[3]=0;num[4]=0;num[5]=0;
              }
            }
        }else if(PermissionUtils.isPermitted("2")||PermissionUtils.isPermitted("3")){
            num[0] = repository.countByJobStatus(id,1 + "%"); //运行中
            num[1] = repository.countByJobStatus(id,4 + "%");//异常
            num[2] = repository.countByJobStatus(id,2 + "%");//暂停中
            num[3] = repository.countByJobStatus(id,5 + "%");//待完善
            num[4] = repository.countByJobStatus(id,0 + "%");//待激活
            num[5] = repository.countByJobStatus(id,3 + "%");//终止中
            if(deptId!=0){
                List<SysJobrela> list= repository.findByUserId(deptId);
                //如果s等于null说明该部门下没有管理员，那么该部门下也没有任务，状态也应该都为0
                if(list!=null&&list.size()>0){
                    num[0] = repository.countByJobStatus(deptId,1 + "%"); //运行中
                    num[1] = repository.countByJobStatus(deptId,4 + "%");//异常
                    num[2] = repository.countByJobStatus(deptId,2 + "%");//暂停中
                    num[3] = repository.countByJobStatus(deptId,5 + "%");//待完善
                    num[4] = repository.countByJobStatus(deptId,0 + "%");//待激活
                    num[5] = repository.countByJobStatus(deptId,3 + "%");//终止中
                }else{
                    num[0]=0;num[1]=0;num[2]=0;num[3]=0;num[4]=0;num[5]=0;
                }
            }
        }else{
            return ToDataMessage.builder().status("0").message("权限不足").build();
        }

        return num;
    }

    @Transactional
    @Override
    public Object queryJobrela(String job_name, Integer current, Integer size) {
        HashMap<Object, Object> map = new HashMap();
        Pageable page = PageRequest.of(current - 1, size);
        if(PermissionUtils.getSysUser()==null){
            return ToDataMessage.builder().status("401").message("请先登录").build();
        }
        Long id=PermissionUtils.getSysUser().getId();//登录用户的id
        List<SysJobrela> data=new ArrayList<>();
        List<SysJobrela> list=new ArrayList<>();

        if(PermissionUtils.isPermitted("1")) {
             data = repository.findByJobNameContainingOrderByIdDesc(job_name, page);
             list = repository.findByJobNameContainingOrderByIdDesc(job_name);
            //具体的条件查询带分页的可以用这个
//        Page<SysJobrela> bookPage = repository.findAll(new Specification<SysJobrela>(){
//            @Override
//            public Predicate toPredicate(Root<SysJobrela> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                Predicate p1 = criteriaBuilder.equal(root.get("jobName").as(String.class), job_name);
//                query.where(criteriaBuilder.and(p1));
//                return query.getRestriction();
//            }
//        },page);
            if (data != null && data.size() > 0) {
                map.put("status", 1);
                map.put("totalCount", list.size());
                map.put("data", data);
            } else {
                map.put("status", 0);
                map.put("message", "任务不存在");
            }
        }else if(PermissionUtils.isPermitted("2")||PermissionUtils.isPermitted("3")){
            data = repository.findByUserIdJobName(id,job_name, page);
            list = repository.findByUserIdJobName(id,job_name);
            if (data != null && data.size() > 0) {
                map.put("status", 1);
                map.put("totalCount", list.size());
                map.put("data", data);
            } else {
                map.put("status", 0);
                map.put("message", "任务不存在");
            }
        }else{
            map.put("status", 0);
            map.put("message", "权限不足");
        }
        return map;
    }

    @Override
    public Object someJobrela(String job_status,Long deptId, Integer current, Integer size) {
        Map<Object, Object> map = new HashMap<>();
        Long id=PermissionUtils.getSysUser().getId();//登录用户的id
        List<SysJobrela> list=new ArrayList<>();
        List<SysJobrela> sysJobrelaList=new ArrayList<>();
        if (current < 1) {
            return ToDataMessage.builder().status("0").message("当前页不能小于1").build();
        } else {
            Pageable page = PageRequest.of(current - 1, size);
            if(PermissionUtils.isPermitted("1")) {
                list = repository.findByJobStatusLikeOrderByIdDesc(job_status + "%", page);
                sysJobrelaList = repository.findByJobStatusLikeOrderByIdDesc(job_status + "%");
               if(deptId!=0){
                List<SysJobrela> data = repository.findByDeptId(deptId);
                if(data!=null&&data.size()>0){
                    list=repository. findByDeptIdAndJobStatus(job_status,deptId,page);
                    sysJobrelaList=repository. findByDeptIdAndJobStatus(job_status,deptId);
                }else{
                    list=null;
                    sysJobrelaList=null;
                }
               }
                map.put("status", "1");
                map.put("totalCount", sysJobrelaList.size());
                map.put("data", list);
            }else if(PermissionUtils.isPermitted("2")||PermissionUtils.isPermitted("3")){
                list = repository.findByUserIdJobStatus(id,job_status, page);
                sysJobrelaList = repository.findByUserIdJobStatus(id,job_status);
                if(deptId!=0){
                   List<SysJobrela> data= repository.findByUserId(deptId);
                   if(data!=null&&data.size()>0){
                       list = repository.findByUserIdJobStatus(deptId,job_status, page);
                       sysJobrelaList = repository.findByUserIdJobStatus(deptId,job_status);
                   }else{
                       list=null;
                       sysJobrelaList=null;
                   }
                }
                map.put("status", "1");
                map.put("totalCount", sysJobrelaList.size());
                map.put("data", list);
            }else{
                map.put("status", 0);
                map.put("message", "权限不足");
            }
                return map;
        }
    }

    @Transactional
    @Override
    public Object start(Long id) {
        System.out.println(id);
        HashMap<Object, Object> map = new HashMap();
        long id1 = id;
        SysJobrela byId = repository.findById(id1);
        String jobStatus = byId.getJobStatus();
        if (!PermissionUtils.isPermitted("1")) {
            if ("0".equals(jobStatus) || "2".equals(jobStatus) || "3".equals(jobStatus)) {
                byId.setJobStatus("11"); // 1代表运行中，11代表开始动作
                repository.save(byId);
                Userlog build = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(byId.getJobName()).operate("启动").jobId(id).build();
                userlogRespository.save(build);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e.getLocalizedMessage());
                }
                Userlog build2 = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(byId.getJobName()).operate("启动成功").jobId(id).build();
                userlogRespository.save(build2);
                //添加任务日志
                logUtil.addJoblog(byId,"com.cn.wavetop.dataone.service.impl.start","启动任务");
                List<SysJobrela> sysJobrelas = new ArrayList<>();
                sysJobrelas.add(byId);
                map.put("status", 1);
                map.put("data", sysJobrelas);
                map.put("message", "激活完成");
            } else {
                map.put("status", 0);
                map.put("message", "无法激活");
            }
        } else {
            map.put("status", "2");
            map.put("message", "权限不足");
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
        String jobStatus = byId.getJobStatus();
        System.out.println(jobStatus);
        if (!PermissionUtils.isPermitted("1")) {
            if ("1".equals(jobStatus)) {
                byId.setJobStatus("21"); //  2 代表暂停中，21代表暂停动作
                repository.save(byId);
                Userlog build = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(byId.getJobName()).operate("暂停").jobId(id).build();
                userlogRespository.save(build);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e.getLocalizedMessage());
                }
                Userlog build2 = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(byId.getJobName()).operate("暂停成功").jobId(id).build();
                userlogRespository.save(build2);
                //添加任务日志
                logUtil.addJoblog(byId,"com.cn.wavetop.dataone.service.impl.pause","暂停任务");
                List<SysJobrela> sysJobrelas = new ArrayList<>();
                sysJobrelas.add(byId);
                map.put("status", 1);
                map.put("data", sysJobrelas);
                map.put("message", "暂停成功");
            } else {
                map.put("status", 0);
                map.put("message", "任务未激活无法暂停");
            }
        } else {
            map.put("status", "2");
            map.put("message", "权限不足");
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
        String jobStatus = byId.getJobStatus();
        if (!PermissionUtils.isPermitted("1")) {
            if (!"1".equals(jobStatus)) {
                byId.setJobStatus("31"); // 3代表终止，31 代表停止功能
                repository.save(byId);
                Userlog build = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(byId.getJobName()).operate("停止").jobId(id).build();
                userlogRespository.save(build);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e.getLocalizedMessage());
                }



                Userlog build2 = Userlog.builder().time(new Date()).user(PermissionUtils.getSysUser().getLoginName()).jobName(byId.getJobName()).operate("停止成功").jobId(id).build();
                userlogRespository.save(build2);
                //添加任务日志
                logUtil.addJoblog(byId,"com.cn.wavetop.dataone.service.impl.end","终止任务");
                List<SysJobrela> sysJobrelas = new ArrayList<>();
                sysJobrelas.add(byId);
                map.put("status", 1);
                map.put("data", sysJobrelas);
                map.put("message", "终止成功");
            } else {
                map.put("status", 0);
                map.put("message", "任务正在运行无法终止");
            }
        } else {
            map.put("status", "2");
            map.put("message", "权限不足");
        }
        return map;
    }


    @Override
    public Object selJobrela(Integer current, Integer size) {
        Map<Object, Object> map = new HashMap<>();
        if (PermissionUtils.isPermitted("2")||PermissionUtils.isPermitted("3")) {
            Pageable pageable = new PageRequest(current - 1, size);
            List<SysJobrela> list = repository.findByUserId(PermissionUtils.getSysUser().getId(), pageable);
            List<SysJobrela> list2 = repository.findByUserId(PermissionUtils.getSysUser().getId());
            map.put("status", "1");
            map.put("totalCount", list2.size());
            map.put("data", list);
        } else {
            map.put("status", "0");
            map.put("data", "权限不足");
        }
        return map;
    }


    //根据用户名或者任务名或者全部的查询任务
    public Object selJobrelaUser(String status,String name){
        //Pageable pageable = new PageRequest(current - 1, size);
        Map<Object,Object> map=new HashMap<>();
        List<SysJobrela> list=new ArrayList<>();
        List<SysJobrela> data=new ArrayList<>();
        if(PermissionUtils.isPermitted("2")) {
            if (status.equals("1")) {
                if (name != null && !"".equals(name)) {
                   // list = repository.findByUserNameJobName(PermissionUtils.getSysUser().getId(), name,PermissionUtils.getSysUser().getDeptId(), pageable);
                    data = repository.findByUserNameJobName(PermissionUtils.getSysUser().getId(), name,PermissionUtils.getSysUser().getDeptId());
                } else {
                    data = repository.findByUserId(PermissionUtils.getSysUser().getId());

                }

            } else if (status.equals("2")) {
                List<SysUser> sysUserList = sysUserRepository.findAllByLoginName(name);
                if(sysUserList!=null&&sysUserList.size()>0) {
                   // list = repository.findByUserId(sysUserList.get(0).getId(), pageable);
                    data = repository.findByUserId(sysUserList.get(0).getId());
                }
            } else if (status.equals("3")) {
                //list = repository.findByUserIdJobName(PermissionUtils.getSysUser().getId(), name, pageable);
                data = repository.findByUserIdJobName(PermissionUtils.getSysUser().getId(), name);

            } else {
                return ToDataMessage.builder().status("2").message("状态不对").build();
            }
            map.put("status", "1");
            map.put("data", data);
            map.put("totalCount", data.size());
            return map;
        }else{
            return ToDataMessage.builder().status("0").message("权限不足").build();
        }
    }

    @Override
    public Object seleJobrelaByUser(Long userId) {
        if(PermissionUtils.isPermitted("2")) {
            List<SysJobrelaUser> list = repository.findJobrelaByUserId(PermissionUtils.getSysUser().getId());
            List<SysJobrelaUser> list1 = repository.findJobrelaByUserId(userId);
            System.out.println(list);
            Iterator<SysJobrelaUser> iterator = list.iterator();
            while(iterator.hasNext()){
                SysJobrelaUser s=  iterator.next();
                if (list1.contains(s)) {
                    iterator.remove();
                }
            }
            int index=0;
            if(list1!=null&&list1.size()>0) {
                for (SysJobrelaUser sysJobrelaUser : list1) {
                    sysJobrelaUser.setChecked("1");
                    list.add(index, sysJobrelaUser);
                    index++;
                }
            }
            return ToData.builder().status("1").data(list).build();
        }else{
           return ToDataMessage.builder().status("0").message("权限不足").build();
        }
    }

    //根据任务查询参与人
    public Object selUserByJobId(Long jobId){
     List<SysUser> sysUserList=sysUserJobrelaRepository.selUserByJobId(jobId);
     return ToData.builder().status("1").data(sysUserList).build();
    }
    @Transactional
    //为任务添加参与人
    public Object addUserByJobId(Long jobId,String userId){
        SysUserJobrela sysUserJobrela=null;
        SysUserJobrela sysUserJobrela2=null;
        SysJorelaUserextra sysJorelaUserextra=null;
        boolean flag=false;
        Optional<SysUser> sysUserlist=null;
        List<Long> list=new ArrayList<>();
        List<Long> userlist=new ArrayList<>();
        userlist = sysUserJobrelaRepository.selUserIdByJobId(jobId);
        List<SysJobrelaRelated> lists= sysJobrelaRelatedRespository.findByMasterJobId(jobId);
        if(userId!=null&&!"".equals(userId)&&!"undefined".equals(userId)) {
            String[] name = userId.split(",");
            for (int i = 0; i < name.length; i++) {
                list.add(Long.valueOf(name[i]));
                flag = sysUserJobrelaRepository.existsAllByUserIdAndJobrelaId(Long.valueOf(name[i]), jobId);
                if (flag) {
                    continue;
                }
                sysUserlist = sysUserRepository.findById(Long.valueOf(name[i]));
                sysUserJobrela = new SysUserJobrela();
                sysUserJobrela.setUserId(sysUserlist.get().getId());
                //sysUserJobrela.setDeptId(sysUserlist.get().getDeptId());
                sysUserJobrela.setJobrelaId(jobId);
                sysUserJobrelaRepository.save(sysUserJobrela);
                if (lists != null && lists.size() > 0) {
                    for (SysJobrelaRelated sysJobrelaRelated : lists) {
                        sysJorelaUserextra=new SysJorelaUserextra();
                        sysJorelaUserextra.setUserId(Long.valueOf(name[i]));
                        sysJorelaUserextra.setJobId(sysJobrelaRelated.getSlaveJobId());
                        sysJorelaUserextraRepository.save(sysJorelaUserextra);
//                        sysUserJobrela2 = new SysUserJobrela();
//                        sysUserJobrela2.setUserId(Long.valueOf(name[i]));
//                        //sysUserJobrela.setDeptId(sysUserlist.get().getDeptId());
//                        sysUserJobrela2.setJobrelaId(sysJobrelaRelated.getSlaveJobId());
//                        sysUserJobrelaRepository.save(sysUserJobrela2);
                    }
                }
            }
        }
            if (userlist != null && userlist.size() > 0) {
                Iterator<Long> iterator = userlist.iterator();
                while (iterator.hasNext()) {
                    Long s = iterator.next();
                    if (list.contains(s)) {
                        iterator.remove();
                    }
                }
                for (Long id : userlist) {
                    sysUserJobrelaRepository.deleteByUserId(id);
                }
            }

        return ToData.builder().status("1").build();
    }


    public Object findById(Long id){
        Optional<SysJobrela> s=repository.findById(id);
        HashMap<Object,Object> map=new HashMap<>();
        map.put("status","1");
        map.put("data",s);
        return map;
    }

    //根据用户id查询参与的任务
    public Object findUserJob(Long userId) {
        System.out.println(new Date()+"start");
        List<SysJobrela> list = repository.findByUserId(userId);
        StringBuffer stringBuffer = new StringBuffer("");
        SysUserJobVo sysUserJobVo = null;
        List<SysUserJobVo> sysUserJobVoList = new ArrayList<>();
        List<SysLog> sysLoglist = new ArrayList<>();
        List<SysUser> sysUserList = new ArrayList<>();
        if (PermissionUtils.isPermitted("2")) {
            for (SysJobrela sysJobrela : list) {
                sysUserJobVo = new SysUserJobVo();
                sysLoglist = sysLogRepository.findByJobIdOrderByCreateDateDesc(sysJobrela.getId());
                if (sysLoglist != null && sysLoglist.size() > 0) {
                    sysUserJobVo.setCreateTime(sysLoglist.get(0).getCreateDate());
                    sysUserJobVo.setCreateUser(sysLoglist.get(0).getUsername());
                }
                sysUserList = sysUserJobrelaRepository.selUserNameByJobId(sysJobrela.getId());
                for (int i = 0; i < sysUserList.size(); i++) {
                    stringBuffer.append(sysUserList.get(i).getLoginName());
                    if (i < sysUserList.size () - 1) {
                        stringBuffer.append(",");
                    }
                }
                sysUserJobVo.setJobId(sysJobrela.getId());
                sysUserJobVo.setJobName(sysJobrela.getJobName());
                sysUserJobVo.setUserName(String.valueOf(stringBuffer));
                stringBuffer.setLength(0);
                sysUserJobVoList.add(sysUserJobVo);
            }
            System.out.println(new Date()+"end");
            return ToData.builder().status("1").data(sysUserJobVoList).build();
        }else{
            return ToDataMessage.builder().status("0").message("权限不足").build();
        }
    }

    //根据用户id查询没有参与的任务
    public Object findUserJobNo(Long userId) {
        StringBuffer stringBuffer = new StringBuffer("");
        SysUserJobVo sysUserJobVo = null;
        List<SysUserJobVo> sysUserJobVoList = new ArrayList<>();
        List<SysLog> sysLoglist = new ArrayList<>();
        List<SysUser> sysUserList = new ArrayList<>();
        if (PermissionUtils.isPermitted("2")) {
            List<SysJobrela> list = repository.findByUserId(PermissionUtils.getSysUser().getId());
            List<SysJobrela> list2 = repository.findByUserId(userId);
            Iterator<SysJobrela> iterator = list.iterator();
            while(iterator.hasNext()){
                SysJobrela s=  iterator.next();
                if (list2.contains(s)) {
                    iterator.remove();
                }
            }
            for (SysJobrela sysJobrela : list) {
                sysUserJobVo = new SysUserJobVo();
                sysLoglist = sysLogRepository.findByJobIdOrderByCreateDateDesc(sysJobrela.getId());
                if (sysLoglist != null && sysLoglist.size() > 0) {
                    sysUserJobVo.setCreateTime(sysLoglist.get(0).getCreateDate());
                    sysUserJobVo.setCreateUser(sysLoglist.get(0).getUsername());
                }
                sysUserList = sysUserJobrelaRepository.selUserNameByJobId(sysJobrela.getId());
                for (int i = 0; i < sysUserList.size(); i++) {
                    stringBuffer.append(sysUserList.get(i).getLoginName());
                    if (i < sysUserList.size() - 1) {
                        stringBuffer.append(",");
                    }
                }
                sysUserJobVo.setJobId(sysJobrela.getId());
                sysUserJobVo.setJobName(sysJobrela.getJobName());
                sysUserJobVo.setUserName(String.valueOf(stringBuffer));
                stringBuffer.setLength(0);
                sysUserJobVoList.add(sysUserJobVo);
            }
            return ToData.builder().status("1").data(sysUserJobVoList).build();
        }else{
            return ToDataMessage.builder().status("0").message("权限不足");
        }
    }


    //首页根据部门查询任务
    @Override
    public Object selJobrelaByDeptIdPage(Long deptId, Integer current, Integer size) {
        Pageable pageable = new PageRequest(current - 1, size);
        Map<Object,Object> map=new HashMap<>();
        List<SysJobrela> list=new ArrayList<>();
        List<SysJobrela> data=new ArrayList<>();
        if(PermissionUtils.isPermitted("1")) {
            if(deptId!=0) {
                list = repository.findByDeptId(deptId, pageable);
                data = repository.findByDeptId(deptId);
            }else{
                Page<SysJobrela> page=repository.findAll(pageable);
                map.put("status", "1");
                map.put("data", page.getContent());
                map.put("totalCount", page.getTotalElements());
                return map;
            }
        }else if(PermissionUtils.isPermitted("2")||PermissionUtils.isPermitted("3")) {
            if(deptId!=0) {
                list = repository.findByUserId(deptId, pageable);
                data = repository.findByUserId(deptId);
            }else{
                list = repository.findByUserId(PermissionUtils.getSysUser().getId(), pageable);
                data = repository.findByUserId((PermissionUtils.getSysUser().getId()));
            }
        }
        map.put("status", "1");
        map.put("data", list);
        map.put("totalCount", data.size());
        return map;
//        }else{
//          return ToDataMessage.builder().status("0").message("权限不足").build();
//        }
    }

    public Object findDbinfoById(Long id){
        SysDbinfo s=repository.findDbinfoById(id);
        return s;
    }
}
