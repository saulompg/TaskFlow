package com.dev.taskflow.Service;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskFinishedDTO;
import com.dev.taskflow.Entity.Task;
import com.dev.taskflow.Repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Deve retornar um TaskDTO com sucesso quando o ID existir")
    void shouldReturnTaskDTOWhenIdExists() {
        // ARRANGE: preparação do cenário
        Long validId = 1L;

        Task mockTask = new Task("Teste Unitário", "Descrição do teste" );
        mockTask.setId(validId);
        mockTask.setCreationDate(LocalDateTime.now());
        mockTask.setFinished(true);

        // ensina o mock a retornar a tarefa
        when(repository.findById(validId)).thenReturn(Optional.of(mockTask));

        // ACT: executar a ação
        TaskDTO result = taskService.getById(validId);

        // ASSERT: validar o retorno do DTO
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(validId);
        assertThat(result.title()).isEqualTo("Teste Unitário");
        assertThat(result.description()).isEqualTo("Descrição do teste");
        assertThat(result.finished()).isTrue();
        // validar interações com o mock
        verify(repository, times(1)).findById(validId);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar uma Exception se o ID não for encontrado")
    void shouldThrowExceptionWhenIdDoesNotExists() {
        // ARRANGE
        Long invalidId = 99L;

        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> taskService.getById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Tarefa não encontrada para o ID " + invalidId);
        verify(repository, times(1)).findById(invalidId);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve criar uma tarefa com sucesso")
    void shouldReturnTarefaDTO() {
        // ARRANGE
        TaskCreateDTO inputDTO = new TaskCreateDTO("Teste Unitário", "Descrição do teste");

        Long  validId = 1L;

        Task savedEntity = inputDTO.toEntity();
        savedEntity.setId(validId);
        savedEntity.setCreationDate(LocalDateTime.now());

        when(repository.save(any(Task.class))).thenReturn(savedEntity);

        // ACT
        TaskDTO result = taskService.createTask(inputDTO);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(validId);
        assertThat(result.title()).isEqualTo(inputDTO.title());

        assertThat(result.description()).isEqualTo(inputDTO.description());
        verify(repository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve deletar uma tarefa com sucesso")
    void shouldDeleteTask() {
        // ARRANGE
        Long validId = 1L;

        Task mockTask = new Task( );
        mockTask.setId(validId);

        when(repository.findById(validId)).thenReturn(Optional.of(mockTask));

        taskService.deleteTask(validId);

        // ASSERT
        verify(repository, times(1)).findById(validId);
        verify(repository, times(1)).delete(mockTask);
    }

    @Test
    @DisplayName("Deve atualizar uma tarefa com sucesso")
    void shouldUpdateTask() {
        // ARRANGE
        TaskCreateDTO inputDTO = new TaskCreateDTO("Update Teste", "Descrição atualizada");

        Long validId = 1L;

        Task mockTask = new Task("Teste Unitário", "Descrição do teste");
        mockTask.setId(validId);

        when(repository.findById(validId)).thenReturn(Optional.of(mockTask));

        // ACT
        TaskDTO updatedTask = taskService.updateTask(validId, inputDTO);

        // ASSERT
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.id()).isEqualTo(validId);
        assertThat(updatedTask.title()).isEqualTo(inputDTO.title());
        assertThat(updatedTask.description()).isEqualTo(inputDTO.description());

        verify(repository, times(1)).findById(validId);
    }

    @Test
    @DisplayName("Deve atualizar o status de uma Tarefa com sucesso")
    void shouldUpdateStatusTask() {
        TaskFinishedDTO inputDTO = new TaskFinishedDTO(true);

        Long validId = 1L;

        Task mockTask = new Task("Teste Unitário", "Descrição do teste");
        mockTask.setId(validId);
        mockTask.setFinished(false);

        when(repository.findById(validId)).thenReturn(Optional.of(mockTask));

        TaskDTO updatedTask = taskService.updateTaskStatus(validId, inputDTO);

        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.finished()).isTrue();

        verify(repository, times(1)).findById(validId);
    }
}

