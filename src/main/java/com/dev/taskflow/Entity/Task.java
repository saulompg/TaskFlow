package com.dev.taskflow.Entity;

import com.dev.taskflow.Enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity // Diz ao Spring que esta classe é uma tabela no banco
@Table(name = "tb_task")
@NoArgsConstructor
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // define Auto-Increment
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDENTE;
    @CreationTimestamp // delega ao Hibernate
    @Column(updatable = false) // Garante que a data de criação nunca mude
    private LocalDateTime creationDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
