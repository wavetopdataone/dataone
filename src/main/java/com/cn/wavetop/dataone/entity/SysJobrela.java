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
public class SysJobrela {
  @Id // 标识主键
  private Long id;
  private String jobName;
  private Long userId;

  private String sourceName;
  private String destName;

  private Long sourceId;
  private Long destId;
  private Long sourceType;
  private Long destType;

  private Long syncRange;
  private Double jobRate;
  private Long jobStatus;


}
