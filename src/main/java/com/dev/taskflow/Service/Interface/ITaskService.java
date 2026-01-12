package com.dev.taskflow.Service.Interface;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;

import java.util.List;

public interface ITaskService {
    public List<TaskDTO> getAll();
    public TaskDTO createTask(TaskCreateDTO taskDTO);
}
