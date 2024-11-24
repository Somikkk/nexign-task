package com.microservice.nexign.olis.worker;

import com.microservice.nexign.olis.models.Task;
import com.microservice.nexign.olis.models.enums.TaskStatus;
import com.microservice.nexign.olis.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskWorker {

    private final TaskRepository taskRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void processTask(Task task) {
        try {
            task.setStatus(TaskStatus.IN_PROGRESS);
            taskRepository.save(task);
            kafkaTemplate.send("task-updates", "Task " + task.getId() + " is now IN_PROGRESS");

            Thread.sleep(task.getDuration());

            task.setStatus(TaskStatus.COMPLETED);
            task.setResult("Task successfully completed");
            taskRepository.save(task);
            kafkaTemplate.send("task-updates", "Task " + task.getId() + " is now COMPLETED");

        } catch (InterruptedException e) {
            task.setStatus(TaskStatus.FAILED);
            task.setResult("Task failed due to an error");
            taskRepository.save(task);
            kafkaTemplate.send("task-updates", "Task " + task.getId() + " failed");
        }
    }
}


