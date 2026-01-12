package com.dev.taskflow.Controller;

import com.dev.taskflow.DTOs.TaskDTO;
import com.dev.taskflow.Service.Interface.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final ITaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> listar() {
        // ResponseEntity permite controlar o status code (200 OK)
        return ResponseEntity.ok(taskService.getAll());
    }

}
