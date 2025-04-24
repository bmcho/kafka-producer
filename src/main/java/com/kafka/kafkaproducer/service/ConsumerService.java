package com.kafka.kafkaproducer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

//    @KafkaListener(topics = "testTopic", groupId = "spring")
//    public void consumer(String msg) {
//        System.out.printf("Subscribed: %s%n", msg);
//    }
}
