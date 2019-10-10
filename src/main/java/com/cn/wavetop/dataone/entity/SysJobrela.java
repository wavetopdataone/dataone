package com.cn.wavetop.dataone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author yongz
 * @Date 2019/10/10、11:45
 */

@Entity // 标识实体
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class SysJobrela {
  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private long id;
  private String jobName;
  private long userId;
  private String sourceName;
  private String destName;
  private long sourceId;
  private long destId;
  private long sourceType;
  private long destType;
  private long syncRange;
  private double jobRate;
  private long jobStatus;


}
