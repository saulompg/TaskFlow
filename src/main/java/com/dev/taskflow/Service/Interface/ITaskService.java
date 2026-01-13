package com.dev.taskflow.Service.Interface;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskFinishedDTO;

import java.util.List;

public interface ITaskService {
    List<TaskDTO> getAll();
    TaskDTO getById(Long id);
    TaskDTO createTask(TaskCreateDTO dto);
    TaskDTO updateTask(Long id, TaskCreateDTO dto);
    void deleteTask(Long id);
    TaskDTO isFinishedTask(Long id, TaskFinishedDTO dto);
}
