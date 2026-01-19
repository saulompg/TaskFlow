package com.dev.taskflow.Repository;

import com.dev.taskflow.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @Modifying // Indica que é uma alteração (UPDATE/DELETE) e não uma busca
    @Query("UPDATE Task t SET t.category = null WHERE t.category.id = :categoryId")
    void removeCategoryReferences(@Param("categoryId") Long categoryId);
}
