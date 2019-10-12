package com.cn.wavetop.dataone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yongz
 * @Date 2019/10/10、11:45
 */

@Entity // 标识实体
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Userlog {
  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String user;
  @Column(name="time",columnDefinition="datetime")
  private Date time;
  private String jobName;
  private String operate;
  private Long jobId;


}
