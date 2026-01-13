package com.dev.taskflow.Controller;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.DTOs.TaskFinishedDTO;
import com.dev.taskflow.Service.Interface.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TaskController {

    private final ITaskService taskService;

    @Operation(
            summary = "Buscar Tarefas",
            description = "Retorna todas as Tarefas disponíveis"
    )
    @ApiResponse(responseCode = "200", description = "Lista de Tarefas")
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll() {
        // ResponseEntity permite controlar o status code (200 OK)
        return ResponseEntity.ok().body(taskService.getAll());
    }

    @Operation(
            summary = "Buscar Tarefa por ID",
            description = "Retorna os dados de uma tarefa específica baseada no ID fornecido"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada (ID inexistente)"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {
        TaskDTO task = taskService.getById(id);
        return ResponseEntity.ok().body(task);
    }

    @Operation(summary = "Criar Tarefa", description = "Criar uma nova Tarefa")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso"),
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
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody @Valid TaskCreateDTO inputDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, inputDTO);

        return ResponseEntity.ok().body(updatedTask);
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

    @Operation(summary = "Atualizar Status", description = "Atualizar o Status da Tarefa")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada (ID inexistente)")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid TaskFinishedDTO inputDTO) {
        TaskDTO updatedTask = taskService.updateTaskStatus(id, inputDTO);
        return ResponseEntity.ok().body(updatedTask);
    }
}
