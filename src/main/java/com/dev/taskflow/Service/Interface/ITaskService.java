package com.dev.taskflow.Service.Interface;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskUpdateDTO;
import com.dev.taskflow.Enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaskService {
    Page<TaskDTO> getTasks(String title, TaskStatus status, Long categoryId, Pageable pageable);
    TaskDTO getById(Long id);
    TaskDTO createTask(TaskCreateDTO dto);
    TaskDTO updateTask(Long id, TaskUpdateDTO dto);
    void deleteTask(Long id);
    TaskDTO updateTaskStatus(Long id, TaskStatus status);
    TaskDTO updateTaskCategory(Long id, Long categoryId);
    TaskDTO removeCategory(Long id);
}
