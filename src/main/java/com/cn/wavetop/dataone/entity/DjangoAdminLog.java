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

public class DjangoAdminLog {

  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private long id;
  private java.sql.Timestamp actionTime;
  private String objectId;
  private String objectRepr;
  private long actionFlag;
  private String changeMessage;
  private long contentTypeId;
  private long userId;

}
