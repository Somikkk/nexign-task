package com.microservice.nexign.olis.scheduler;

import com.microservice.nexign.olis.models.Task;
import com.microservice.nexign.olis.models.enums.TaskStatus;
import com.microservice.nexign.olis.repository.TaskRepository;
import com.microservice.nexign.olis.worker.TaskWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomTaskScheduler {

    private final TaskRepository taskRepository;
    private final TaskWorker taskWorker;

    @Scheduled(fixedDelayString = "5000")
    public void scheduledTask() {
        log.info("Looking for new tasks to process...");
        List<Task> tasks = taskRepository.findByStatus(TaskStatus.NEW);

        tasks.forEach(task -> {
            log.info("Processing task: {}", task.getId());
            new Thread(() -> taskWorker.processTask(task)).start();
        });
    }

}
