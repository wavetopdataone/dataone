package com.cn.wavetop.dataone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
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
public class SysJobinfo {
  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private Long id;
  private String players;
  @Column(nullable = false)
  private Long syncRange;
  private Long syncWay;
  private String readFrequency;

  private Long readBegin;
  private String readWay;
  private Long dataEnc;
  private String maxSourceRead;
  private Long destWriteConcurrentNum;

  private String maxDestWrite;
  private Long destCaseSensitive;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @CreatedDate
  private Date beginTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @CreatedDate
  private Date endTime;
  @Column(nullable = false)
  private Long jobId;


}
