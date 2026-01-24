package com.dev.taskflow.Specification;

import com.dev.taskflow.Entity.Category;
import com.dev.taskflow.Entity.Task;
import com.dev.taskflow.Entity.User;
import com.dev.taskflow.Enums.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    // Filtro por Título (LIKE)
    public static Specification<Task> titleContains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };
    }

    // Filtro por Status (Exact Match)
    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Task> hasCategory(Category category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) return null;
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    public static Specification<Task> hasUser(User user) {
        return (root, query, criteriaBuilder) -> {
            if (user == null) return null;
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }
}
