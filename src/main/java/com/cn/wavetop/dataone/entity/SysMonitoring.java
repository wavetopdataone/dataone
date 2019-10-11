package com.cn.wavetop.dataone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
@Data

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
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private java.sql.Timestamp optTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
