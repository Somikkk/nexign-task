package com.microservice.nexign.olis.dto;

import com.microservice.nexign.olis.models.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDto {
    private Long id;
    private String name;
    private Long duration;
    private TaskStatus status;
    private String result;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
