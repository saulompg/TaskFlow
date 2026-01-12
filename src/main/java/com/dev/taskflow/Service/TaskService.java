package com.dev.taskflow.Service;

import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.Repository.TaskRepository;
import com.dev.taskflow.Service.Interface.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository repository;

    @Override
    public List<TaskDTO> getAll() {
        return repository.findAll().stream().map(task ->
                new TaskDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.isFinished(),
                        task.getCreationDate()
                )
        ).toList();
    }
}
