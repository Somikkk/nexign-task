package com.microservice.nexign.olis.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Long id) {
        super("Task with ID " + id + " not found");
    }
}
