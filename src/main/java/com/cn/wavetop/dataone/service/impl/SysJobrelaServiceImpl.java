package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.aop.MyLog;
import com.cn.wavetop.dataone.aop.ServiceLogAspect;
import com.cn.wavetop.dataone.dao.*;
import com.cn.wavetop.dataone.entity.*;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysJobrelaService;
import com.cn.wavetop.dataone.util.PermissionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.awt.print.Book;
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
    public Object addJobrela(SysJobrela sysJobrela,String names) {
        // long id = sysJobrela.getId();
        //  SysJobrela sysJobrelabyId = repository.findById(id);
        //SysJobrela sysJobrelabyJobName = repository.findByJobName();
        HashMap<Object, Object> map = new HashMap();
        List<SysUser> sysUserList=new ArrayList<>();
        SysUserJobrela sysUserJobrela=null;
        if (PermissionUtils.isPermitted("2")) {
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
                sysJobrela.setJobStatus("5");
                SysJobrela save = repository.save(sysJobrela);
                if(names!=null&&!"".equals(names)&&!"undefined".equals(names)){
                    String[] name=names.split(",");
                    for(int i=0;i<name.length;i++){
                        sysUserList=sysUserRepository.findAllByLoginName(name[i]);
                        sysUserJobrela=new SysUserJobrela();
                        sysUserJobrela.setUserId(sysUserList.get(0).getId());
                        sysUserJobrela.setDeptId(sysUserList.get(0).getDeptId());
                        sysUserJobrela.setJobrelaId(save.getId());
                        sysUserJobrelaRepository.save(sysUserJobrela);
                    }
                }
                sysUserJobrela = new SysUserJobrela();
                sysUserJobrela.setUserId(PermissionUtils.getSysUser().getId());
                sysUserJobrela.setDeptId(PermissionUtils.getSysUser().getDeptId());
                sysUserJobrela.setJobrelaId(save.getId());
                sysUserJobrelaRepository.save(sysUserJobrela);
                map.put("status", 1);
                map.put("message", "添加成功");
                map.put("data", save);
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
        if (!PermissionUtils.isPermitted("1")) {
            long id = sysJobrela.getId();
            // 查看该任务是否存在，存在修改更新任务，不存在新建任务
            if (repository.existsByJobName(sysJobrela.getJobName())) {

                // 查看端
                SysDbinfo source = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getSourceName(), 0);
                //目标端
                SysDbinfo dest = sysDbinfoRespository.findByNameAndSourDest(sysJobrela.getDestName(), 1);

                SysJobrela data = repository.findByJobName(sysJobrela.getJobName());
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
                    Userlog build = Userlog.builder().user("admin").jobName(JobrelabyId.getJobName()).time(new Date()).operate("删除").build();
                    userlogRespository.save(build);
                    repository.deleteById(id);
                    sysJobinfoRespository.deleteByJobId(id);
                    sysUserJobrelaRepository.deleteByJobrelaId(id);
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
        } else {
            map.put("status", "2");
            map.put("message", "权限不足");
        }
        return map;
    }

    @Override
    public Object jobrelaCount() {
        int[] num = new int[6];
        Long id=PermissionUtils.getSysUser().getId();//登录用户的id
        if(PermissionUtils.isPermitted("1")){
            num[0] = repository.countByJobStatusLike(1 + "%"); //运行中
            num[1] = repository.countByJobStatusLike(4 + "%");//异常
            num[2] = repository.countByJobStatusLike(2 + "%");//暂停中
            num[3] = repository.countByJobStatusLike(5 + "%");//待完善
            num[4] = repository.countByJobStatusLike(0 + "%");//待激活
            num[5] = repository.countByJobStatusLike(3 + "%");//终止中
        }else if(PermissionUtils.isPermitted("2")||PermissionUtils.isPermitted("3")){
            num[0] = repository.countByJobStatus(id,1 + "%"); //运行中
            num[1] = repository.countByJobStatus(id,4 + "%");//异常
            num[2] = repository.countByJobStatus(id,2 + "%");//暂停中
            num[3] = repository.countByJobStatus(id,5 + "%");//待完善
            num[4] = repository.countByJobStatus(id,0 + "%");//待激活
            num[5] = repository.countByJobStatus(id,3 + "%");//终止中
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
    public Object someJobrela(String job_status, Integer current, Integer size) {
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
                map.put("status", "1");
                map.put("totalCount", sysJobrelaList.size());
                map.put("data", list);
            }else if(PermissionUtils.isPermitted("2")||PermissionUtils.isPermitted("3")){
                list = repository.findByUserIdJobStatus(id,job_status, page);
                sysJobrelaList = repository.findByUserIdJobStatus(id,job_status);
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

    @Override
    public Object selJobrelaByUserId(Long userId, String name, Integer current, Integer size) {
        return null;
    }

//    //根据用户id查询已经分配的任务 没写完
//    @Override
//    public Object selJobrelaByUserId(Long userId, String name, Integer current, Integer size) {
//        Map<Object, Object> map = new HashMap<>();
//        if (PermissionUtils.isPermitted("2")) {
//            Pageable pageable = new PageRequest(current - 1, size, Sort.Direction.DESC, "id");
//            List<SysJobrela> list = repository.findByUserId(PermissionUtils.getSysUser().getId(), pageable);
//            List<SysJobrela> list2 = repository.findByUserId(PermissionUtils.getSysUser().getId());
//            map.put("status", "1");
//            map.put("totalCount", list2.size());
//            map.put("data", list);
//        } else {
//            map.put("status", "0");
//            map.put("data", "权限不足");
//        }
//        return map;
//    }

    //根据用户名或者任务名或者全部的查询任务
    public Object selJobrelaUser(String status,String name,Integer current,Integer size){
        Pageable pageable = new PageRequest(current - 1, size);
        Map<Object,Object> map=new HashMap<>();
        List<SysJobrela> list=new ArrayList<>();
        List<SysJobrela> data=new ArrayList<>();
        if(PermissionUtils.isPermitted("2")) {
            if (status.equals("1")) {
                if (name != null && !"".equals(name)) {
                    list = repository.findByUserNameJobName(PermissionUtils.getSysUser().getId(), name,PermissionUtils.getSysUser().getDeptId(), pageable);
                    data = repository.findByUserNameJobName(PermissionUtils.getSysUser().getId(), name,PermissionUtils.getSysUser().getDeptId());
                } else {
                    list = repository.findByUserId(PermissionUtils.getSysUser().getId(), pageable);
                    data = repository.findByUserId(PermissionUtils.getSysUser().getId());
                }

            } else if (status.equals("2")) {
                List<SysUser> sysUserList = sysUserRepository.findAllByLoginName(name);
                if(sysUserList!=null&&sysUserList.size()>0) {
                    list = repository.findByUserId(sysUserList.get(0).getId(), pageable);
                    data = repository.findByUserId(sysUserList.get(0).getId());
                }
            } else if (status.equals("3")) {
                list = repository.findByUserIdJobName(PermissionUtils.getSysUser().getId(), name, pageable);
                data = repository.findByUserIdJobName(PermissionUtils.getSysUser().getId(), name);

            } else {
                return ToDataMessage.builder().status("2").message("状态不对").build();
            }
            map.put("status", "1");
            map.put("data", list);
            map.put("totalCount", data.size());
            return map;
        }else{
            return ToDataMessage.builder().status("0").message("权限不足").build();
        }
    }
}
