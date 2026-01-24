package com.dev.taskflow.Service;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskUpdateDTO;
import com.dev.taskflow.Entity.Category;
import com.dev.taskflow.Entity.Task;
import com.dev.taskflow.Entity.User;
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


import static com.dev.taskflow.Util.SecurityUtil.getAuthenticatedUser;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<TaskDTO> getTasks(String title, TaskStatus status, Long categoryId, Pageable pageable) {
        User user = getAuthenticatedUser();

        Specification<Task> spec = TaskSpecification.hasUser(user);

        if (title != null && !title.isBlank()) {
            spec = spec.and(TaskSpecification.titleContains(title));
        }

        if (status != null) {
            spec = spec.and(TaskSpecification.hasStatus(status));
        }

        if (categoryId != null) {
            var category = categoryRepository.findById(categoryId);

            if (category.isEmpty()) return Page.empty();

            if (!category.get().getUser().getId().equals(user.getId())) return Page.empty();

            spec = spec.and(TaskSpecification.hasCategory(category.get()));
        }

        return taskRepository.findAll(spec, pageable)
            .map(this::toDTO);
    }

    @Override
    public TaskDTO getById(Long id) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

        return toDTO(task);
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskCreateDTO dto) {
        User user = getAuthenticatedUser();

        Task newTask = new Task(dto.title(), dto.description());
        newTask.setUser(user);

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

            if (!category.getUser().getId().equals(user.getId())) {
                throw new SecurityException("Categoria não encontrada");
            }

            newTask.setCategory(category);
        }

        Task savedTask = taskRepository.save(newTask);
        return toDTO(savedTask);
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long id, TaskUpdateDTO dto) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        return toDTO(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskDTO updateTaskStatus(Long id, TaskStatus status) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

        task.setStatus(status);

        return toDTO(task);
    }

    @Override
    @Transactional
    public TaskDTO updateTaskCategory(Long id, Long categoryId) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Categoria não encontrada");
        }

        task.setCategory(category);
        category.addTask(task);
        return toDTO(task);
    }

    @Override
    @Transactional
    public TaskDTO removeCategory(Long id) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }

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
