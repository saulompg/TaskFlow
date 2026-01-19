package com.dev.taskflow.Service;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskUpdateDTO;
import com.dev.taskflow.Entity.Category;
import com.dev.taskflow.Entity.Task;
import com.dev.taskflow.Enums.TaskStatus;
import com.dev.taskflow.Repository.CategoryRepository;
import com.dev.taskflow.Repository.TaskRepository;
import com.dev.taskflow.Service.Interface.ITaskService;
import com.dev.taskflow.Specification.TaskSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<TaskDTO> getTasks(String title, TaskStatus status, Long categoryId, Pageable pageable) {
        Specification<Task> spec = ((root, query, cb) -> cb.conjunction());

        if (title != null && !title.isBlank()) {
            spec = spec.and(TaskSpecification.titleContains(title));
        }

        if (status != null) {
            spec = spec.and(TaskSpecification.hasStatus(status));
        }

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + categoryId));
            spec = spec.and(TaskSpecification.hasCategory(category));
        }

        return taskRepository.findAll(spec, pageable)
            .map(this::toDTO);
    }

    @Override
    public TaskDTO getById(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        return toDTO(task);
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskCreateDTO dto) {
        Task newTask = new Task(dto.title(), dto.description());

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + dto.categoryId()));

            newTask.setCategory(category);
        }

        Task savedTask = taskRepository.save(newTask);
        return toDTO(savedTask);
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long id, TaskUpdateDTO dto) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        return toDTO(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));
        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskDTO updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        task.setStatus(status);

        return toDTO(task);
    }

    @Override
    @Transactional
    public TaskDTO updateTaskCategory(Long id, Long categoryId) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + categoryId));

        task.setCategory(category);
        category.addTask(task);
        return toDTO(task);
    }

    @Override
    @Transactional
    public TaskDTO removeCategory(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));

        Category category = task.getCategory();
        task.setCategory(null);
        category.removeTask(task);

        return toDTO(task);
    }

    private TaskDTO toDTO(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getCreationDate(),
            task.getCategory() != null ? task.getCategory().getId() : null,
            task.getCategory() != null ? task.getCategory().getName() : "Sem Categoria"
        );
    }
}
