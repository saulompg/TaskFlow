package com.dev.taskflow.Controller;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskUpdateDTO;
import com.dev.taskflow.Enums.TaskStatus;
import com.dev.taskflow.Service.Interface.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TaskController {

    private final ITaskService taskService;

    @Operation(
            summary = "Buscar Tarefas",
            description = "Retorna Tarefas com filtros opcionais de titulo e status"
    )
    @ApiResponse(responseCode = "200", description = "Lista de Tarefas")
    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long categoryId,
            @ParameterObject @PageableDefault(sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // ResponseEntity permite controlar o status code (200 OK)
        return ResponseEntity.ok().body(taskService.getTasks(title, status, categoryId, pageable));
    }

    @Operation(
            summary = "Buscar Tarefa por ID",
            description = "Retorna os dados de uma tarefa específica baseada no ID fornecido"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada (ID inexistente)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {
        TaskDTO task = taskService.getById(id);
        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "Criar Tarefa", description = "Criar uma nova Tarefa")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping
    public ResponseEntity<TaskDTO> create(
            @RequestBody @Valid TaskCreateDTO inputDTO,
            UriComponentsBuilder uriBuilder
    ) {
        TaskDTO createdTask =  taskService.createTask(inputDTO);

        // Cria a URI: localhost:8080/tasks/{id}
        var uri = uriBuilder.path("/tasks/{id}")
                .buildAndExpand(createdTask.id())
                .toUri();

        // Retorna 201 Created com o Header Location e o corpo
        return ResponseEntity.created(uri).body(createdTask);
    }

    @Operation(summary = "Atualizar Tarefa", description = "Atualizar dados da Tarefa")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada (ID inexistente)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody @Valid TaskUpdateDTO inputDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, inputDTO);

        return ResponseEntity.ok().body(updatedTask);
    }

    @Operation(summary = "Atualizar Status", description = "Atualizar o Status da Tarefa")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada (ID inexistente)")
    })
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<TaskDTO> updateStatus(@PathVariable Long id, @PathVariable TaskStatus status) {
        TaskDTO updatedTask = taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok().body(updatedTask);
    }

    @Operation(summary = "Atualizar Categoria", description = "Atualizar a Categoria da Tarefa")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado (ID inexistente)")
    })
    @PatchMapping("/{id}/category/{categoryId}")
    public ResponseEntity<TaskDTO> updateCategory(@PathVariable Long id, @PathVariable Long categoryId) {
        TaskDTO updatedTask = taskService.updateTaskCategory(id, categoryId);
        return ResponseEntity.ok().body(updatedTask);
    }

    @Operation(summary = "Remove Categoria", description = "Remover uma Categoria da Tarefa")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado (ID inexistente)")
    })
    @DeleteMapping("/{id}/category")
    public ResponseEntity<TaskDTO> removeCategory(@PathVariable Long id) {
        TaskDTO task = taskService.removeCategory(id);
        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "Deletar Tarefa", description = "Deletar uma Tarefa do Banco")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada (ID inexistente)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
