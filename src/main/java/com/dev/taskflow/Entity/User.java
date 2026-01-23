package com.dev.taskflow.Entity;

import com.dev.taskflow.Enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Setter
    @Column(nullable = false, length = 20)
    private String firstName;

    @Setter
    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, length = 80)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private List<Category> categories;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;

    public User(String email, String password, String firstName, String lastName, UserRole role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;

        this.categories = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }
}
