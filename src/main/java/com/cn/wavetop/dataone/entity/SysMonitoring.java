package com.cn.wavetop.dataone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name ="sys_monitoring",
        uniqueConstraints={@UniqueConstraint(columnNames={"jobId","syncRange","sourceTable"})})
public class SysMonitoring {
  @Id // 标识主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
  private long id;
  private Long jobId;
  private String jobName;
  private Long syncRange;
  private String sourceTable;
  private String destTable;
  private Long sqlCount;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date optTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date needTime;
  private Double fulldataRate;
  private Double incredataRate;
  private Double stocksdataRate;
  private Double tableRate;
  private Long readRate;
  private Long disposeRate;
  private Long jobStatus;
  private Long readData;
  private Long writeData;
  private Long errorData;


}
