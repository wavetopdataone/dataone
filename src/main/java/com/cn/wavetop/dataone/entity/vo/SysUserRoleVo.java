package com.cn.wavetop.dataone.entity.vo;

import lombok.Data;

@Data
public class SysUserRoleVo {
    private long userId;
    private long roleId;
    private String userName;
    private String roleName;
    private String perms;

    public SysUserRoleVo(long userId, long roleId,String userName, String roleName, String perms) {
        this.userId = userId;
        this.roleId = roleId;
        this.userName = userName;
        this.roleName = roleName;
        this.perms=perms;
    }
}
