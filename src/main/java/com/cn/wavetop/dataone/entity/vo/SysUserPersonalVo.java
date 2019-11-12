package com.cn.wavetop.dataone.entity.vo;

import lombok.Data;

@Data
public class SysUserPersonalVo {
    private Long userId;
    private String userName;
    private String deptName;
    private String email;
    private Integer countJob;

    public SysUserPersonalVo(Long userId, String userName, String deptName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.deptName = deptName;
        this.email = email;
    }
}
