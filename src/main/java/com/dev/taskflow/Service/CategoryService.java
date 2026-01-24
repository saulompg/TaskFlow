package com.dev.taskflow.Service;

import com.dev.taskflow.DTOs.CategoryCreateDTO;
import com.dev.taskflow.DTOs.CategoryDTO;
import com.dev.taskflow.DTOs.CategoryUpdateDTO;
import com.dev.taskflow.Entity.Category;
import com.dev.taskflow.Entity.User;
import com.dev.taskflow.Repository.CategoryRepository;
import com.dev.taskflow.Repository.TaskRepository;
import com.dev.taskflow.Service.Interface.ICategoryService;
import com.dev.taskflow.Specification.CategorySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dev.taskflow.Util.SecurityUtil.getAuthenticatedUser;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<CategoryDTO> getCategories(String name) {
        User user = getAuthenticatedUser();

        Specification<Category> spec = CategorySpecification.hasUser(user);

        if (name != null && !name.isBlank()) {
            spec = spec.and(CategorySpecification.nameContains(name));
        }

        return categoryRepository.findAll(spec).stream()
            .map(this::toDTO).toList();
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        User user = getAuthenticatedUser();

        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Categoria não encontrada");
        }

        return toDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryCreateDTO dto) {
        User user = getAuthenticatedUser();

        Category category = dto.toEntity();
        category.setUser(user);
        category = categoryRepository.save(category);

        return toDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryUpdateDTO dto) {
        User user = getAuthenticatedUser();

        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Categoria não encontrada");
        }

        category.setName(dto.name());
        category.setColor(dto.color());

        return toDTO(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        User user = getAuthenticatedUser();

        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Categoria não encontrada");
        }

        taskRepository.removeCategoryReferences(id);
        categoryRepository.delete(category);
    }

    private CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getColor()
        );
    }
}
