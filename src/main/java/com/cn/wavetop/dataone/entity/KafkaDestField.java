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
public class KafkaDestField {
    //
    @Id // 标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自定义生成
    private Long id;
    @Column(nullable = false)
    private Long kafkaDestId;
    @Column(nullable = false)
    private String fieldName;
    @Column(nullable = false)
    private String fieldType;
    @Column(nullable = false)
    private Double fieldLength;
    @Column(nullable = false)
    private String fieldNotnull;
    @Column(nullable = false)
    private String fieldDesensitization;
    @Column(nullable = false)
    private Long jobId;

}

