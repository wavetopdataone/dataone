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

public class DjangoAdminLog {

  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private Long id;
  private java.sql.Timestamp actionTime;
  private String objectId;
  @Column(nullable = false)
  private String objectRepr;
  @Column(nullable = false)
  private Long actionFlag;
  @Column(nullable = false)
  private String changeMessage;
  private Long contentTypeId;
  @Column(nullable = false)
  private Long userId;

}
