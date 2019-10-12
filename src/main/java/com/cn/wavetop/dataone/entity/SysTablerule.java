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
@Data
public class SysTablerule {
  @Id // 标识主键

  private long id;
  private long jobId;
  private String sourceTable;
  private String sourceName;
  private String viewName;
  private String schema;
  private long hasPrimaryKey;
  private long check;
  private long sourOrDest;
  private String destTable;


}
