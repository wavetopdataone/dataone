package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.SysDeptRepository;
import com.cn.wavetop.dataone.dao.SysUserRepository;
import com.cn.wavetop.dataone.dao.SysUserlogRepository;
import com.cn.wavetop.dataone.entity.SysDept;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.SysUserlog;
import com.cn.wavetop.dataone.entity.vo.SysUserDept;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysUserlogService;
import com.cn.wavetop.dataone.util.PermissionUtils;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class SysUserlogServiceImpl implements SysUserlogService {
    @Autowired
    private SysUserlogRepository sysUserlogRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysDeptRepository sysDeptRepository;


    //超管或者管理员进来查看管理日志
    @Override
    public Object findAllSysUserlog(Integer current, Integer size) {
        Map<Object, Object> map = new HashMap<>();
        List<SysUserlog> sysUserlogs=new ArrayList<>();
        List<SysUserlog> sysUserlogList=new ArrayList<>();
        List<SysUserlog> data=new ArrayList<>();

        Pageable page = PageRequest.of(current - 1, size, Sort.Direction.DESC, "id");
        Integer sum=0;
        if(PermissionUtils.isPermitted("1")){
            Page<SysUserlog> list=sysUserlogRepository.findAll(page);
            map.put("status", "1");
            map.put("totalCount", list.getTotalElements());
            map.put("data", list.getContent());
        }else if(PermissionUtils.isPermitted("2")){
            Optional<SysDept> sysDept= sysDeptRepository.findById(PermissionUtils.getSysUser().getDeptId());
            sysUserlogList= sysUserlogRepository.findByDeptName(sysDept.get().getDeptName(),page);
            data= sysUserlogRepository.findByDeptName(sysDept.get().getDeptName());
            map.put("status", "1");
            map.put("totalCount", data.size());
            map.put("data", sysUserlogList);
        }else{
            return ToDataMessage.builder().status("0").message("权限不足").build();
        }
        return map;
    }

    //根据日期查询
    public Object findLog(Long deptId,String operation,String StartTime,String endTime,Integer current,Integer size){
        Pageable page = PageRequest.of(current - 1, size, Sort.Direction.DESC, "id");
        List<SysUserlog> sysUserlogList=new ArrayList<>();
        Map<Object,Object> map=new HashMap<>();
        StringBuilder stringBuilder=new StringBuilder(endTime);
       Integer a= Integer.parseInt(stringBuilder.substring(8,9));
       Integer b= Integer.parseInt(stringBuilder.substring(9,10));
       if(b==9){
           a+=1;
           b=0;
       }else{
           b+=1;
       }
       stringBuilder.replace(8,9,String.valueOf(a));
        stringBuilder.replace(9,10,String.valueOf(b));
        System.out.println(stringBuilder);
        if(!PermissionUtils.isPermitted("3")){
            Specification<SysUserlog> querySpecifi = new Specification<SysUserlog>() {
                @Override
                public Predicate toPredicate(Root<SysUserlog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<>();

                    //大于或等于传入时间
                    if(StartTime!=null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("createDate").as(String.class), StartTime));
                    }
                    //小于或等于传入时间
                    if(endTime!=null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("createDate").as(String.class), String.valueOf(stringBuilder)));
                    }
                    if(deptId!=0) {
                        if (PermissionUtils.isPermitted("1")) {
                            Optional<SysDept> sysDept = sysDeptRepository.findById(deptId);
                            predicates.add(cb.equal(root.get("deptName").as(String.class), sysDept.get().getDeptName()));
                        }else if(PermissionUtils.isPermitted("2")){
                            Optional<SysUser> sysUser=sysUserRepository.findById(deptId);
                            predicates.add(cb.equal(root.get("username").as(String.class), sysUser.get().getLoginName()));
                        }
                    }
                    if(!"所有".equals(operation)){
                        predicates.add(cb.equal(root.get("operation").as(String.class), operation));
                    }
                    if(PermissionUtils.isPermitted("2")){
                        Optional<SysDept> sysDept= sysDeptRepository.findById(PermissionUtils.getSysUser().getDeptId());
                        predicates.add(cb.equal(root.get("deptName").as(String.class), sysDept.get().getDeptName()));

                    }
                    // and到一起的话所有条件就是且关系，or就是或关系
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            Page<SysUserlog> sysUserlogPage=sysUserlogRepository.findAll(querySpecifi,page);
            map.put("status","1");
            map.put("data",sysUserlogPage.getContent());
            map.put("totalCount",sysUserlogPage.getTotalElements());
        }else {
            return ToDataMessage.builder().status("0").message("权限不足").build();
        }
        return map;
    }

}
