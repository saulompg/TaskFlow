package com.dev.taskflow.Specification;

import com.dev.taskflow.Entity.Category;
import com.dev.taskflow.Entity.User;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> nameContains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<Category> hasUser(User user) {
        return (root, query, criteriaBuilder) -> {
            if (user == null) return null;
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }
}
