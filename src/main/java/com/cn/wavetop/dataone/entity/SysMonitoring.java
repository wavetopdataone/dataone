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


public class SysMonitoring {
  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private long id;
  private long jobId;
  private String jobName;
  private long syncRange;
  private String sourceTable;
  private String destTable;
  private long sqlCount;
  private java.sql.Timestamp optTime;
  private java.sql.Timestamp needTime;
  private double fulldataRate;
  private double incredataRate;
  private double stocksdataRate;
  private double tableRate;
  private long readRate;
  private long disposeRate;
  private long jobStatus;
  private long readData;
  private long writeData;
  private long errorData;


}
