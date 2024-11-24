package com.microservice.nexign.olis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microservice.nexign.olis.dto.TaskRequestDto;
import com.microservice.nexign.olis.dto.TaskResponseDto;
import com.microservice.nexign.olis.models.Task;
import com.microservice.nexign.olis.service.TaskProducer;
import com.microservice.nexign.olis.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskProducer taskProducer;


    @Operation(summary = "Получение задачи по ID", description = "Возвращает информацию о задаче по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создание задачи", description = "Создает новую задачу и отправляет в Kafka")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<TaskResponseDto> createAndSendTask(@RequestBody @Valid TaskRequestDto requestDto) {
        // Создание задачи и сохранение в БД
        TaskResponseDto createdTask = taskService.createTask(requestDto);

        // Отправка задачи в Kafka
        taskProducer.sendTask("tasks", toJson(createdTask));

        // Возврат результата
        return ResponseEntity.ok(createdTask);
    }


    @Operation(summary = "Получение всех задач", description = "Возвращает список всех задач")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))
    })
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // Преобразует объект в строку JSON
    private String toJson(TaskResponseDto taskResponseDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Формат ISO-8601
            return objectMapper.writeValueAsString(taskResponseDto);
        } catch (JsonProcessingException e) {
            log.error("Ошибка при сериализации объекта: {}", taskResponseDto, e);
            throw new RuntimeException("Ошибка при сериализации объекта в JSON", e);
        }
    }

}
