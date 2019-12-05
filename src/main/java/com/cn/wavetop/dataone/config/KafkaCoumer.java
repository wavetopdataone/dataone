package com.cn.wavetop.dataone.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaCoumer {
    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecords<String, String> record) {
        for(final ConsumerRecord recordss: record){
            System.out.println("dsadsaxuezihao"+recordss.key());
            System.out.println("dsadsaxuezihao"+recordss.value());
        }

    }
}
