package com.microservice.nexign.olis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.nexign.olis.dto.TaskRequestDto;
import com.microservice.nexign.olis.models.Task;
import com.microservice.nexign.olis.models.enums.TaskStatus;
import com.microservice.nexign.olis.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskRepository taskRepository;

    @KafkaListener(topics = "task-topic", groupId = "task-service-group")
    public void listen(String message) {
        log.info("Received message from Kafka: {}", message);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TaskRequestDto taskRequest = objectMapper.readValue(message, TaskRequestDto.class);
            Task task = new Task();
            task.setName(taskRequest.getName());
            task.setDuration(taskRequest.getDuration());
            task.setStatus(TaskStatus.NEW);
            taskRepository.save(task);
            log.info("Task saved: {}", task);
        } catch (Exception e) {
            log.error("Error processing Kafka message: {}", e.getMessage());
        }
    }
}

