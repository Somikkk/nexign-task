package com.microservice.nexign.olis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendTask(String topic, String message) {
        log.info("Sending message to Kafka topic: {}: {}", topic, message);
        kafkaTemplate.send(topic,message);
    }
}
