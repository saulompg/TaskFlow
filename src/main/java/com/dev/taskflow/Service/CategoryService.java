package com.dev.taskflow.Service;

import com.dev.taskflow.DTOs.CategoryCreateDTO;
import com.dev.taskflow.DTOs.CategoryDTO;
import com.dev.taskflow.Entity.Category;
import com.dev.taskflow.Repository.CategoryRepository;
import com.dev.taskflow.Service.Interface.ICategoryService;
import com.dev.taskflow.Specification.CategorySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService implements ICategoryService {

    private final CategoryRepository repository;

    @Override
    public List<CategoryDTO> getCategories(String name) {
        Specification<Category> spec = ((root, query, cb) -> cb.conjunction());

        if (name != null && !name.isBlank()) {
            spec = spec.and(CategorySpecification.nameContains(name));
        }

        return repository.findAll(spec).stream()
            .map(this::toDTO).toList();
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada para o id " + id));

        return toDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryCreateDTO dto) {
        Category category = dto.toEntity();
        category = repository.save(category);
        return toDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryCreateDTO dto) {
        Category category = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada para o id " + id));

        category.setName(dto.name());
        category.setColor(dto.color());

        return toDTO(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada para o id " + id));

        repository.delete(category);
    }

    private CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getColor()
        );
    }
}
