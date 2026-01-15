package com.dev.taskflow.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_category")
@NoArgsConstructor
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 100)
    private String color;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Task> tasks;

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
