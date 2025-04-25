package com.kafka.kafkaproducer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String, Object> defaultKafkaTemplate;
    private final KafkaTemplate<String, Object> purchaseLogKafkaTemplate;
    private final KafkaTemplate<String, Object> watchingAdLogKafkaTemplate;

    public void pub(String msg) {
        defaultKafkaTemplate.send("testTopic", msg);
    }

    public void sendJoinMsg(String topic, Object msg) {
        defaultKafkaTemplate.send(topic, msg);
    }

    public void sendMsgForWatchingAdLog(String topicNm, Object msg) {
        watchingAdLogKafkaTemplate.send(topicNm, msg);
    }

    public void sendMsgForPurchaseLog(String topicNm, Object msg) {
        purchaseLogKafkaTemplate.send(topicNm, msg);
    }

}
