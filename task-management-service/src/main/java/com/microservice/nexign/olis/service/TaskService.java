package com.microservice.nexign.olis.service;

import com.microservice.nexign.olis.dto.TaskRequestDto;
import com.microservice.nexign.olis.dto.TaskResponseDto;
import com.microservice.nexign.olis.exceptions.InvalidTaskException;
import com.microservice.nexign.olis.exceptions.TaskNotFoundException;
import com.microservice.nexign.olis.models.Task;
import com.microservice.nexign.olis.models.enums.TaskStatus;
import com.microservice.nexign.olis.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskResponseDto createTask(TaskRequestDto request) {
        if(request.getName() == null || request.getName().isBlank()) {
            throw new InvalidTaskException("Task name cannot be empty");
        }

        if(request.getDuration() == null || request.getDuration() <= 0) {
            throw new InvalidTaskException("Task duration must be grater than 0");
        }
        Task task = Task.builder()
                .name(request.getName())
                .duration(request.getDuration())
                .status(TaskStatus.NEW)
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToResponseDTO(savedTask);
    }

//    public Optional<TaskResponseDto> getTaskById(Long id) {
//        return taskRepository.findById(id).map(this::mapToResponseDTO);
//    }

    public Optional<TaskResponseDto> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::mapToResponseDTO)
                .or(() -> {
                    throw new TaskNotFoundException(id);
                });
    }

    private TaskResponseDto mapToResponseDTO(Task task) {
        TaskResponseDto response = new TaskResponseDto();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setDuration(task.getDuration());
        response.setStatus(task.getStatus());
        response.setResult(task.getResult());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
