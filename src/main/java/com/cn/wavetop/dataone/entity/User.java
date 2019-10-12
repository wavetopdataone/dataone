package com.cn.wavetop.dataone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity // 标识实体
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id // 标识主键

    private long id;
    private String username;
    private String password;
    private double balance;

}
