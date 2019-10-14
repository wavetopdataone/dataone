package com.cn.wavetop.dataone.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SysDbinfo {
  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private Long id;
  private String host;
  private String user;
  private String password;
  private String name;
  private String dbname;

  @Column(name="\"schema\"")
  private String schema;
  private Long port;
  @Column(name="sour_or_dest")
  @JsonProperty(value = "sour_or_dest")
  private Long sourDest;
  private Long type;

}
