package com.dev.taskflow.Service.Interface;

import com.dev.taskflow.DTOs.TaskDTO;

import java.util.List;

public interface ITaskService {
    public List<TaskDTO> getAll();
}
