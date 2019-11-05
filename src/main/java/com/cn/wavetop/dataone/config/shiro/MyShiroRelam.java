package com.cn.wavetop.dataone.config.shiro;



import com.cn.wavetop.dataone.dao.SysUserRepository;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.vo.SysUserRoleVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroRelam extends AuthorizingRealm {

    @Autowired
    private SysUserRepository sysUserRespository;
//    @Autowired
//    private SysRoleRepository sysRoleRepository;
//    @Autowired
//    private SysUserRoleRepository sysUserRoleRepository;

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        SysUser tbUsers=(SysUser) principalCollection.getPrimaryPrincipal();
        List<SysUserRoleVo> list=sysUserRespository.findByLoginName(tbUsers.getLoginName());
        for(SysUserRoleVo sysRole:list){
            authorizationInfo.addRole(sysRole.getRoleName());
            authorizationInfo.addStringPermission(sysRole.getPerms());
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username=(String)authenticationToken.getPrincipal();
        System.out.println(username);
        System.out.println(authenticationToken.getCredentials());
        List<SysUser> userInfo= sysUserRespository.findAllByLoginName(username);
        if(userInfo==null||userInfo.size()<=0){
            return null;
        }
//        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        //这里会去校验密码是否正确
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.get(0), //用户名
                userInfo.get(0).getPassword(),//密码
//                userInfo.
                getName()
        );
        return authenticationInfo;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());

    }
}
