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

    private Long kafkaDestId;

    private String fieldName;

    private String fieldType;

    private Double fieldLength;

    private String fieldNotnull;

    private String fieldDesensitization;

    private Long jobId;

}

