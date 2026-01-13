package com.dev.taskflow.Specification;

import com.dev.taskflow.Entity.Task;
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
    public static Specification<Task> hasStatus(Boolean finished) {
        return (root, query, criteriaBuilder) -> {
            if (finished == null) return null;
            return criteriaBuilder.equal(root.get("finished"), finished);
        };
    }
}
