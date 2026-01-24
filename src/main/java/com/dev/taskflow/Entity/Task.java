package com.dev.taskflow.Entity;

import com.dev.taskflow.Enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity // Diz ao Spring que esta classe é uma tabela no banco
@Table(name = "tb_task")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // define Auto-Increment
    @Setter
    private Long id;

    @Column(nullable = false, length = 100)
    @Setter
    private String title;

    @Column(columnDefinition = "TEXT")
    @Setter
    private String description;

    @Enumerated(EnumType.STRING)
    @Setter
    private TaskStatus status = TaskStatus.PENDENTE;

    @CreationTimestamp // delega ao Hibernate
    @Column(updatable = false) // Garante que a data de criação nunca mude
    private LocalDateTime creationDate;

    @ManyToOne
    @Setter
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Setter
    private Category category;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
