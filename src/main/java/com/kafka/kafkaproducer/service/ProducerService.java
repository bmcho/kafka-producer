package com.kafka.kafkaproducer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String,Object> kafkaTemplate;
    public void pub(String msg) {
        kafkaTemplate.send("testTopic", msg);
    }

    public void sendJoinMsg(String topic, Object msg) {
        kafkaTemplate.send(topic, msg);
    }
}
