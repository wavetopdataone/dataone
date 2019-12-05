package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.KafkaDestField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaDestFieldRepository extends JpaRepository<KafkaDestField,Long> {
}
