package com.dev.taskflow.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity // Diz ao Spring que esta classe é uma tabela no banco
@Table(name = "tb_task")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // define Auto-Increment
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean isFinished = false;
    private LocalDateTime creationDate = LocalDateTime.now();

}
