package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.ErrorLogRespository;
import com.cn.wavetop.dataone.entity.*;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.service.ErrorLogService;
import com.cn.wavetop.dataone.util.DateUtil;
import com.cn.wavetop.dataone.util.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @Author yongz
 * @Date 2019/10/11、11:17
 */
@Service
public class ErrorLogServiceImpl  implements ErrorLogService {
    @Autowired
    private ErrorLogRespository repository;
    @Override
    public Object getErrorlogAll() {

        return ToData.builder().status("1").data(repository.findAll()).build();
    }
    //条件查询
    @Override
    public Object getCheckError(Long jobId,String tableName,String startTime,String endTime) {
        // Pageable page = PageRequest.of(current - 1, size, Sort.Direction.DESC, "id");
        List<ErrorLog> sysErrorlogList=new ArrayList<>();
        Map<Object,Object> map=new HashMap<>();
        String endDate=null;
        System.out.println(endTime+"-------------------heng");
        if(endTime!=null&&!"".equals(endTime)) {
            endDate= DateUtil.dateAdd(endTime,1);
        }
        if(PermissionUtils.isPermitted("1")||PermissionUtils.isPermitted("2")){
            String finalEndDate = endDate;
            Specification<ErrorLog> querySpecifi = new Specification<ErrorLog>() {
                @Override
                public Predicate toPredicate(Root<ErrorLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<>();
                    //大于或等于传入时间
                    if(startTime!=null&&!"".equals(startTime)) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("optTime").as(String.class),startTime));
                    }
                    //小于或等于传入时间
                    if(endTime!=null&&!"".equals(endTime)) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("optTime").as(String.class), finalEndDate));
                    }

                        predicates.add(cb.like(root.get("sourceName").as(String.class), "%"+tableName+"%"));

                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                    criteriaQuery.orderBy(cb.desc(root.get("optTime")));

                    // and到一起的话所有条件就是且关系，or就是或关系
                    return criteriaQuery.getRestriction();
                    // and到一起的话所有条件就是且关系，or就是或关系
                    // return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            List<ErrorLog> sysUserlogPage=repository.findAll(querySpecifi);
            map.put("status","1");
            map.put("data",sysUserlogPage);
            map.put("totalCount",sysUserlogPage.size());
        }else {
            map.put("status","0");
            map.put("message","权限不足");
        }
        return map;
    }
    @Transactional
    @Override
    public Object addErrorlog(ErrorLog errorLog) {

        if (repository.existsById(errorLog.getId())) {
            return ToData.builder().status("0").message("任务已存在").build();
        } else {
            ErrorLog saveData = repository.save(errorLog);
            HashMap<Object, Object> map = new HashMap();
            map.put("status", 1);
            map.put("message", "添加成功");
            map.put("data", saveData);
            return map;
        }
    }

    @Override
    public Object editErrorlog(ErrorLog errorLog) {
        HashMap<Object, Object> map = new HashMap();

        long id = errorLog.getId();

        // 查看该任务是否存在，存在修改更新任务，不存在新建任务
        if (repository.existsById(id)) {
            ErrorLog data = repository.findById(id);
            data.setJobId(errorLog.getJobId());
            data.setContent(errorLog.getContent());
            data.setDestName(errorLog.getDestName());
            data.setJobName(errorLog.getJobName());
            data.setOptContext(errorLog.getOptContext());
            data.setOptTime(errorLog.getOptTime());
            data.setOptType(errorLog.getOptType());
            data.setSchame(errorLog.getSchame());
            data.setSourceName(errorLog.getSourceName());
            data = repository.save(data);

            map.put("status", 1);
            map.put("message", "修改成功");
            map.put("data", data);
        } else {
            ErrorLog data = repository.save(errorLog);
            map.put("status", 2);
            map.put("message", "添加成功");
            map.put("data", data);
        }
        return map;
    }

    @Override
    public Object deleteErrorlog(long id) {
        HashMap<Object, Object> map = new HashMap();

        // 查看该任务是否存在，存在删除任务，返回数据给前端
        if (repository.existsById(id)) {
            repository.deleteById(id);
            map.put("status", 1);
            map.put("message", "删除成功");
        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return map;
    }
    //根据任务id查询表名，行数，和错误的数据量
    @Override
    public Object queryErrorlog(Long jobId) {
        HashMap<Object, Object> map = new HashMap();
        Set<String> set=new HashSet<>();
        List<ErrorLog> data = repository.findByJobId(jobId);
        //todo 根据任务id查询出有多少张表出现错误
        if (data != null&&data.size()>0) {
            for(ErrorLog errorLog:data){
                set.add(errorLog.getSourceName());
            }
            map.put("status", 1);
            map.put("data", data);
            map.put("total", data.size());
            map.put("table",set);//表名
        } else {
            map.put("status", 0);
            map.put("message", "任务不存在");
        }
        return  map;
    }

    //根据id查询错误详情
    @Override
    public Object selErrorlogById(Long id) {
        HashMap<Object, Object> map = new HashMap();
        Optional<ErrorLog> sysError=repository.findById(id);
        map.put("data",sysError);
        map.put("status","1");
        return map;
    }
}
