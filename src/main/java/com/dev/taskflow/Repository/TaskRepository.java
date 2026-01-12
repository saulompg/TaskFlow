package com.dev.taskflow.Repository;

import com.dev.taskflow.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> { }
