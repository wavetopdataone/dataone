package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.KafkaDestTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaDestTableRepository extends JpaRepository<KafkaDestTable,Long> {
}
