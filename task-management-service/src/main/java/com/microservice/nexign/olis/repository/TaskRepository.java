package com.microservice.nexign.olis.repository;

import com.microservice.nexign.olis.models.Task;
import com.microservice.nexign.olis.models.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus taskStatus);
}
