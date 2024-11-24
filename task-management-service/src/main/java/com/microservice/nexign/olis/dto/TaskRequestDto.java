package com.microservice.nexign.olis.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequestDto {
    @NotBlank(message = "Task name is required")
    private String name;


    @NotNull(message = "Task duration is required")
    @Min(value = 1, message = "Duration must be at least 1 millisecond")
    private Long duration;
}
