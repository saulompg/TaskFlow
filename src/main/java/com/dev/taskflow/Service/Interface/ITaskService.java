package com.dev.taskflow.Service.Interface;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskFinishedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaskService {
    Page<TaskDTO> getAll(String title, Boolean finished, Pageable pageable);
    TaskDTO getById(Long id);
    TaskDTO createTask(TaskCreateDTO dto);
    TaskDTO updateTask(Long id, TaskCreateDTO dto);
    void deleteTask(Long id);
    TaskDTO updateTaskStatus(Long id, TaskFinishedDTO dto);
}
