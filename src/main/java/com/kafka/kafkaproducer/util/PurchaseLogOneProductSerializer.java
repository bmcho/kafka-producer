package com.kafka.kafkaproducer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.kafkaproducer.vo.PurchaseLogOneProduct;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class PurchaseLogOneProductSerializer implements Serializer<PurchaseLogOneProduct> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, PurchaseLogOneProduct data) {
        try {
            if (data == null){
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SecurityException("Exception Occured");
        }
    }

    @Override
    public void close() {
    }

}