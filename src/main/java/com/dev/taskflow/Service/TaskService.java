package com.dev.taskflow.Service;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskFinishedDTO;
import com.dev.taskflow.Entity.Task;
import com.dev.taskflow.Repository.TaskRepository;
import com.dev.taskflow.Service.Interface.ITaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository repository;

    @Override
    public List<TaskDTO> getAll() {
        return repository.findAll().stream()
            .map(this::toDTO).toList();
    }

    @Override
    public TaskDTO getById(Long id) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada para o ID " + id));

        return toDTO(task);
    }

    @Override
    public TaskDTO createTask(TaskCreateDTO dto) {
        Task newTask = new Task(dto.title(), dto.description());

        Task savedTask = repository.save(newTask);
        return toDTO(savedTask);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskCreateDTO dto) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada para o ID " + id));

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        Task savedTask = repository.save(task);
        return toDTO(savedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada para o ID " + id));
        repository.delete(task);
    }

    @Override
    public TaskDTO isFinishedTask(Long id, TaskFinishedDTO dto) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada para o ID " + id));

        task.setFinished(dto.finished());
        Task savedtask = repository.save(task);
        return toDTO(savedtask);
    }

    private TaskDTO toDTO(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isFinished(),
            task.getCreationDate()
        );
    }
}
