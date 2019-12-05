package com.cn.wavetop.dataone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // 标识实体
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaDestTable {
    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
    private Long id;
    @Column(nullable = false)
    private Long jobId;
    @Column(nullable = false)
    private Long destDbid;
    @Column(nullable = false)
    private String destTable;
}
