package com.cn.wavetop.dataone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
  private Long jobId;
  private String sourceTable;
  private String sourceName;
  private String viewName;
  @Column(name = "`schema`")
  private String schema;
  private Long hasPrimaryKey;
  @Column(name = "`check`")
  private Long check;
  @Column(name = "`sour_or_dest`")
  private Long sourOrDest;
  private String destTable;


}
