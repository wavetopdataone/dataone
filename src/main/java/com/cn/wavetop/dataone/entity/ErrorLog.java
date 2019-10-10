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

public class ErrorLog {
  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private long id;
  private long jobId;
  private String sourceName;
  private String schame;
  private String destName;
  private String optType;
  private String optContext;
  private java.sql.Timestamp optTime;
  private String content;
  private String jobName;


}
