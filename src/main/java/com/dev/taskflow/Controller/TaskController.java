package com.dev.taskflow.Controller;

import com.dev.taskflow.DTOs.TaskCreateDTO;
import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.Service.Interface.ITaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final ITaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll() {
        // ResponseEntity permite controlar o status code (200 OK)
        return ResponseEntity.ok().body(taskService.getAll());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> create(
            @RequestBody @Valid TaskCreateDTO inputDTO,
            UriComponentsBuilder uriBuilder
    ) {
        TaskDTO createdTask =  taskService.createTask(inputDTO);

        // Cria a URI: localhost:8080/tasks/{id}
        var uri = uriBuilder.path("/tasks/id")
                .buildAndExpand(createdTask.id())
                .toUri();

        // Retorna 201 Created com o Header Location e o corpo
        return ResponseEntity.created(uri).body(createdTask);
    }

}
