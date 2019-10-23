package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.TbUsers;
import com.cn.wavetop.dataone.entity.vo.SysUserRoleVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    @Query("select new com.cn.wavetop.dataone.entity.vo.SysUserRoleVo(s.id,r.id,s.loginName,r.roleName,r.roleKey,m.perms) from SysUser as s,SysRole as r,SysUserRole as e,SysMenu as m,SysRoleMenu rm where s.id=e.userId and r.id=e.roleId and r.id=rm.roleId and rm.menuId=m.id and s.loginName=:loginName")
    List<SysUserRoleVo> findByLoginName(String loginName);

    SysUser findByUserNameAndPassword(String userName,String password);
    List<SysUser> findAllByLoginName(String loginName);
    int deleteByLoginName(String loginName);
}
