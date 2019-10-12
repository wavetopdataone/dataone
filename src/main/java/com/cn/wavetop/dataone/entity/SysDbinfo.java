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
public class SysDbinfo {
  @Id // 标识主键
  private long id;
  private String host;
  private String user;
  private String password;
  private String name;

  private String dbname;

  @Column(name="\"schema\"")
  private String schema;
  private long port;
  @Column(name="sour_or_dest")
  private long sourDest;
  private long type;

}
