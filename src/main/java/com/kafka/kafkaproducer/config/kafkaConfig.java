package com.kafka.kafkaproducer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableKafka
@EnableKafkaStreams
public class kafkaConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-test");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "15.164.214.129:9094,54.180.118.88:9094,3.37.127.193:9094");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 3);
//        config.put(StreamsConfig.NUM_STANDBY_REPLICAS_CONFIG, 3);
        return new KafkaStreamsConfiguration(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "15.164.214.129:9094,54.180.118.88:9094,3.37.127.193:9094");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerConfig);
    }
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        Map<String, Object> consumerConfig = new HashMap<>();
//        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "15.164.214.129:9094,54.180.118.88:9094,3.37.127.193:9094");
//        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(consumerConfig);
//    }
}
