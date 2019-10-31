package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.config.shiro.CredentialMatcher;
import com.cn.wavetop.dataone.dao.SysRoleMenuRepository;
import com.cn.wavetop.dataone.dao.SysUserJobrelaRepository;
import com.cn.wavetop.dataone.dao.SysUserRepository;
import com.cn.wavetop.dataone.dao.SysUserRoleRepository;
import com.cn.wavetop.dataone.entity.SysRole;
import com.cn.wavetop.dataone.entity.SysRoleMenu;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.SysUserRole;
import com.cn.wavetop.dataone.entity.vo.SysUserDept;
import com.cn.wavetop.dataone.entity.vo.SysUserRoleVo;
import com.cn.wavetop.dataone.entity.vo.ToData;
import com.cn.wavetop.dataone.entity.vo.ToDataMessage;
import com.cn.wavetop.dataone.service.SysUserService;
import com.cn.wavetop.dataone.util.PermissionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SessionDAO sessionDAO;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;
    @Autowired
    private SysRoleMenuRepository sysRoleMenuRepository;
    @Autowired
    private SysUserJobrelaRepository sysUserJobrelaRepository;
    @Autowired
    public SysUserServiceImpl(SessionDAO a) {
        this.sessionDAO = a;

    }

    @Override
    public Object login(String name, String password) {
        SysUser sysUser=new SysUser();
        List<SysUserRoleVo> s=new ArrayList<>();
        Map<Object,Object> map=new HashMap<>();
        //验证用户登录的是否是邮箱登录
        if(PermissionUtils.flag(name)) {
            sysUser = sysUserRepository.findByEmail(name);
            if (sysUser != null) {
                name = sysUser.getLoginName();
            } else {
                return ToDataMessage.builder().status("0").message("用户不存在").build();
            }
        }
        List<SysUser> list=sysUserRepository.findAllByLoginName(name);
        UsernamePasswordToken token=new UsernamePasswordToken(name,password);
        //ThreadContext.bind(SecurityUtils.getSubject());
        Subject subject= SecurityUtils.getSubject();
//        Session sessionss=subject.getSession();
//        System.out.println(sessionss.getId()+"dsadsaxuezihao.................");
        try{
            if(list!=null&&list.size()>0) {
                if(list.get(0).getStatus().equals("1")) {
                    subject.login(token);
                    //PermissionUtils.setSubject(subject);
//                    Collection<Session> sessions = sessionDAO.getActiveSessions();
//                    for (Session session : sessions) {
//                        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
//                                .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//                        SysUser userDO = (SysUser) principalCollection.getPrimaryPrincipal();
//
//                        System.out.println(userDO+"dsadsadsa-----------------");
//                        System.out.println( s +"dsadsadsa-----------------");
//
//                    }
//                    System.out.println("---------------------");
                    Serializable tokenId = subject.getSession().getId();
                    System.out.println(tokenId);
                   //美其效果
                    subject.getSession().setTimeout(36000*60*1000);
                    //查的是用户角色权限三张表
                    s = sysUserRepository.findByLoginName(name);
//                    //比较用户是否有super这个权限
                    map.put("status", "1");
                    map.put("authToken", tokenId);
                    map.put("data", s);
                }else{
                    map.put("status", "3");
                    map.put("message", "账号被冻结");
                }

            }else{
                map.put("status", "0");
                map.put("message", "用户不存在");
            }
        }catch (Exception e){
            map.put("status","2");
            map.put("message","密码错误");
        }
        return map;
    }

    //退出
    @Override
    public Object loginOut() {
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        return ToDataMessage.builder().status("1").message("退出成功").build();
    }

    //根据用户权限查询用户(权限1：显示所有管理员的信息；2显示该部门的用户)
    @Override
    public Object findAll() {
        Map<Object,Object> map=new HashMap<>();
        List<SysUserDept> list=new ArrayList<>();
        String perms=null;
        if(PermissionUtils.isPermitted("1")){
            list=sysUserRepository.findUserByUserPerms("2");

            SysUserDept sysUserDept=new SysUserDept(PermissionUtils.getSysUser().getId(),PermissionUtils.getSysUser().getDeptId(),PermissionUtils.getSysUser().getLoginName(),PermissionUtils.getSysUser().getPassword(),PermissionUtils.getSysUser().getEmail(),"","超级管理员");
            list.add(0,sysUserDept);

            map.put("status","1");
            map.put("data",list);
        }else if(PermissionUtils.isPermitted("2")){
            list=sysUserRepository.findUserByPerms(PermissionUtils.getSysUser().getId(),"1");
            map.put("status","1");
            map.put("data",list);
        }else{
            map.put("status","0");
            map.put("message","权限不足");
        }

        return map;
    }

    //根据查找该部门下的所有成员
    @Override
    public Object findById() {
       List<SysUser> list= sysUserRepository.findUser(PermissionUtils.getSysUser().getDeptId());
       return ToData.builder().status("1").data(list).build();
    }


    @Transactional
    @Override
    public Object update(SysUser sysUser) {
        List<SysUser> list = sysUserRepository.findAllByLoginName(sysUser.getLoginName());
        Optional<SysUser> sysUser1 = sysUserRepository.findById(sysUser.getId());
        if (list != null && list.size() > 0) {
            return ToDataMessage.builder().status("0").message("用户已存在").build();
        } else {
            sysUser1.get().setLoginName(sysUser.getLoginName());
            sysUser1.get().setEmail(sysUser.getEmail());
            sysUser1.get().setUserName(sysUser.getLoginName());
            String[] saltAndCiphertext = CredentialMatcher.encryptPassword(sysUser.getPassword());
            sysUser1.get().setSalt(saltAndCiphertext[0]);
            sysUser1.get().setPassword(saltAndCiphertext[1]);
            sysUser1.get().setStatus("1");
            sysUser1.get().setUpdateUser(PermissionUtils.getSysUser().getLoginName());
            sysUser1.get().setUpdateTime(PermissionUtils.getSysUser().getCreateTime());
            sysUserRepository.save(sysUser1.get());
            return ToDataMessage.builder().status("1").message("修改成功").build();
        }
    }


    //写的添加，还没有放上去
    @Transactional
    @Override
    public Object addSysUser(SysUser sysUser,String id){
        System.out.println(sysUser+"wocaocaocoa"+id);
        Long ids=Long.valueOf(id);
        List<SysUser> list=sysUserRepository.findAllByLoginName(sysUser.getLoginName());
        HashMap<Object,Object> map=new HashMap<>();
        SysUserRole sysUserRole=new SysUserRole();
        SysRoleMenu sysRoleMenu=new SysRoleMenu();

        if(list!=null&&list.size()>0) {
            return ToDataMessage.builder().status("0").message("用户已存在").build();
        }else{
            String[] saltAndCiphertext = CredentialMatcher.encryptPassword(sysUser.getPassword());
            sysUser.setSalt(saltAndCiphertext[0]);
            sysUser.setPassword(saltAndCiphertext[1]);
            sysUser.setUserName(sysUser.getLoginName());
            sysUser.setStatus("1");
            sysUser.setCreateTime(new Date());
            sysUser.setCreateUser(PermissionUtils.getSysUser().getLoginName());
//            map.put("status","1");
//            map.put("message","添加成功");
        }
        List<SysUser> sysUser1=new ArrayList<>();
        //用户权限不能是编辑者
        if(!PermissionUtils.isPermitted("3")){
            if(PermissionUtils.isPermitted("1")){
                //判断超级管理员创建的是否是管理员 2是管理员角色的id
                 if(ids==2){
                     SysUser suser = sysUserRepository.save(sysUser);
                     sysUser1=sysUserRepository.findAllByLoginName(sysUser.getLoginName());
                     sysUserRole.setUserId(sysUser1.get(0).getId());
                     sysUserRole.setRoleId(ids);
                     sysUserRoleRepository.save(sysUserRole);
                     sysRoleMenu.setMenuId(ids);
                     sysRoleMenu.setRoleId(ids);
                     sysRoleMenuRepository.save(sysRoleMenu);
                     map.put("status","1");
                     map.put("perms","1");
                     map.put("id",suser.getId());
                     map.put("message","添加成功");
                 }else{
                     return ToDataMessage.builder().status("0").message("超级管理员不能创建编辑者角色").build();
                 }
            }else if(PermissionUtils.isPermitted("2")){
                if(ids==3){
                    SysUser suser = sysUserRepository.save(sysUser);
                    suser.setDeptId(PermissionUtils.getSysUser().getDeptId());
                    sysUserRepository.save(suser);
                    sysUser1=sysUserRepository.findAllByLoginName(sysUser.getLoginName());
                    sysUserRole.setUserId(sysUser1.get(0).getId());
                    sysUserRole.setRoleId(ids);
                    sysUserRoleRepository.save(sysUserRole);
                    map.put("status","1");
                    map.put("perms","2");
                    map.put("id",suser.getId());
                    map.put("message","添加成功");
                }else{
                    return ToDataMessage.builder().status("0").message("管理员不能创建超级管理员角色").build();
                }
            }else{
                return ToDataMessage.builder().status("0").message("用户没有角色和权限").build();
            }
        }else{
            return ToDataMessage.builder().status("0").message("编辑者不能创建角色").build();
        }

       return map;
    }

//    @Transactional
//    @Override
//    public Object addSysUser(SysUser sysUser) {
//       List<SysUser> list=sysUserRepository.findAllByLoginName(sysUser.getLoginName());
//        HashMap<String,String> map=new HashMap<>();
//        if(list!=null&&list.size()>0) {
//            map.put("status","0");
//            map.put("message","用户已存在");
//        }else{
//            String[] saltAndCiphertext = CredentialMatcher.encryptPassword(sysUser.getPassword());
//            sysUser.setSalt(saltAndCiphertext[0]);
//            sysUser.setPassword(saltAndCiphertext[1]);
//            sysUser.setStatus("1");
//            sysUser.setCreateTime(new Date());
//            SysUser suser = sysUserRepository.save(sysUser);
//            map.put("status","1");
//            map.put("message","添加成功");
//        }
//        return map;
//
//    }
    @Transactional
    @Override
    public Object delete(String name) {
        try{
            if(PermissionUtils.isPermitted("2")){
                List<SysUser> list=sysUserRepository.findAllByLoginName(name);
            if(list!=null&&list.size()>0) {
                int result= sysUserRepository.deleteByLoginName(name);
                 sysUserRoleRepository.deleteByUserId(list.get(0).getId());
                if(result>0){
                    return ToDataMessage.builder().status("1").message("删除成功").build();
                }else{
                    return ToDataMessage.builder().status("2").message("删除失败").build();
                }
            }else{
                return ToDataMessage.builder().status("0").message("用户不存在").build();
            }
            }else{
                return ToDataMessage.builder().status("3").message("超级管理员不能删除编辑者").build();

            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ToDataMessage.builder().status("0").message("发生错误").build();
        }

    }

    //根据用户名查找角色权限
    @Override
    public Object findRolePerms(String userName) {
        List<SysUserRoleVo> s=  sysUserRepository.findByLoginName(userName);
        return s;
    }

    //超级管理员对管理员的模糊查询或者该管理员所在部门的用户的模糊查询
    @Override
    public Object findByUserName(String userName) {
        List<SysUserDept> list=new ArrayList<>();
        List<SysUserDept> sysUserDepts=new ArrayList<>();
        if(PermissionUtils.isPermitted("1")){
            //超级管理员对管理员的模糊查询
            list=sysUserRepository.findByUserName(userName);

        }else if(PermissionUtils.isPermitted("2")){
            //该管理员所在部门的用户的模糊查询
            list=sysUserRepository.findByDeptUserName(PermissionUtils.getSysUser().getDeptId(),userName);
        }else{
           return ToDataMessage.builder().status("0").message("权限不足").build();
        }
        return ToData.builder().status("1").data(list).build();
    }

    //根据部门Id查询该部门下的用户
    @Override
    public Object findUserByDept(Long deptId){
        List<SysUserDept> list=new ArrayList<>();
        if(PermissionUtils.isPermitted("1")){
            list= sysUserRepository.findUserByDeptId("2",deptId);
        }else if(PermissionUtils.isPermitted("2")){
            list= sysUserRepository.findUserByDeptId("3",PermissionUtils.getSysUser().getDeptId());
       }else{
            return ToDataMessage.builder().status("0").message("权限不足").build();
        }
      return ToData.builder().status("1").data(list).build();
    }
    //超级管理员为管理员选择分组（id是用户id，我在第一页下一步保存了用户并把id传给前端 ）（deptid能穿就不要deptName）
    @Transactional
    @Override
    public Object updateUser(Long id,Long DeptId) {
        if(PermissionUtils.isPermitted("1")) {
            List<SysUser> list = sysUserRepository.findUserByDeptIdAndRoleKey(Long.valueOf(2),DeptId);
            if (list != null && list.size() > 0) {
                return ToDataMessage.builder().status("0").message("该分组下已有管理员");
            } else {
                Optional<SysUser> sysUser = sysUserRepository.findById(id);
                sysUser.get().setDeptId(DeptId);
                sysUserRepository.save(sysUser.get());
                return ToDataMessage.builder().status("1").message("用户分组成功").build();
            }
        }else{
            return ToDataMessage.builder().status("2").message("权限不足").build();
        }
    }

    //冻结用户或者解冻 //冻结用户status：0是冻结用户
    @Override
    public Object updateStatus(Long id, String status) {
        //查询冻结的用户是什么角色
        List<SysRole> list=sysUserRepository.findUserById(id);
        Optional<SysUser> sysUser=sysUserRepository.findById(id);
        Map<Object,Object> map=new HashMap<>();
//        if(list==null||list.size()<=0) {
//            return ToDataMessage.builder().status("3").message("请先选择用户").build();
//        }
        if(PermissionUtils.isPermitted("1")){

                if (list.get(0).getRoleKey().equals("2")) {
                    //解冻还是冻结
                    if (sysUser.get().getStatus().equals("1")) {
                        map.put("status", "1");
                        map.put("message", "冻结成功");
                    } else {
                        map.put("status", "2");
                        map.put("message", "解冻成功");
                    }
                    sysUser.get().setStatus(status);
                    sysUserRepository.save(sysUser.get());
                } else {
                    map.put("status", "0");
                    map.put("message", "超级管理员不能冻结编辑者");
                }

        }else if(PermissionUtils.isPermitted("2")){
            if(list.get(0).getRoleKey().equals("3")){
                if(sysUser.get().getStatus().equals("1")) {
                    map.put("status","1");
                    map.put("message","冻结成功");
                }else{
                    map.put("status","2");
                    map.put("message","解冻成功");
                }
                sysUser.get().setStatus(status);
                sysUserRepository.save(sysUser.get());
            }else{
                map.put("status","0");
                map.put("message","管理员只能冻结编辑者");
            }
        }else{
            map.put("status","3");
            map.put("message","权限不足");
        }
        return map;
    }

    //根据用户id查询用户详细的信息
    public Object selSysUser(Long userId){
        Map<Object,Object> map=new HashMap<>();
        List<SysUserDept> sysUser=new ArrayList<>();
        if(PermissionUtils.isPermitted("1")){
           sysUser=sysUserRepository.findUserByUserIdAndRoleKey(Long.valueOf(2),userId);
          map.put("status","1");
          map.put("data",sysUser);
        }else if(PermissionUtils.isPermitted("2")){
            sysUser=sysUserRepository.findUserByUserIdAndRoleKey(Long.valueOf(3),userId);
            map.put("status","1");
            map.put("data",sysUser);
        }else{
            ToDataMessage.builder().status("0").message("权限不足").build();
        }

        return map;
    }

    //移交团队 id是管理员 userid是编辑者id
    @Transactional
    @Override
    public Object HandedTeam(Long id, Long userId) {
        if(PermissionUtils.isPermitted("1")){
            sysUserRepository.deleteById(id);
            sysUserRoleRepository.deleteByUserId(userId);
            sysUserJobrelaRepository.deleteByUserId(userId);
            sysUserRepository.updataById(id,userId);
            return  ToDataMessage.builder().status("1").message("团队已移交").build();
        }else{
           return  ToDataMessage.builder().status("2").message("权限不足").build();
        }
    }
}
