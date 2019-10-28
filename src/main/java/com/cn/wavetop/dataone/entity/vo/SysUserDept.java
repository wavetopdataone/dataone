package com.cn.wavetop.dataone.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Data
public class SysUserDept {
    private static final long serialVersionUID = 1L;

    private Long userId;

    /** 部门ID */
    private Long deptId;


    /** 登录名称 */
    private String loginName;


    /** 用户邮箱 */
    private String email;
    /** 部门Name */
    private String deptName;
    /** 角色 */
    private String roleName;

    public SysUserDept(Long userId, Long deptId, String loginName, String email, String deptName, String roleName) {
        this.userId = userId;
        this.deptId = deptId;
        this.loginName = loginName;
        this.email = email;
        this.deptName = deptName;
        this.roleName = roleName;
    }
}
