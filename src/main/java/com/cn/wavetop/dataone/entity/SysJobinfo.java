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

public class SysJobinfo {
  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private long id;
  private String players;
  private long syncRange;
  private long syncWay;
  private String readFrequency;
  private long readBegin;
  private String readWay;
  private long dataEnc;
  private String maxSourceRead;
  private long destWriteConcurrentNum;
  private String maxDestWrite;
  private long destCaseSensitive;
  private java.sql.Timestamp beginTime;
  private java.sql.Timestamp endTime;
  private long jobId;


}
