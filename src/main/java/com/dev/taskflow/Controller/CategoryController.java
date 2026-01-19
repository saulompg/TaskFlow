package com.dev.taskflow.Controller;

import com.dev.taskflow.DTOs.CategoryCreateDTO;
import com.dev.taskflow.DTOs.CategoryDTO;
import com.dev.taskflow.DTOs.CategoryUpdateDTO;
import com.dev.taskflow.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento das categorias")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Buscar Categorias",
            description = "Retorna Categorias com filtro opcional de nome"
    )
    @ApiResponse(responseCode = "200", description = "Lista de Categorias")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@RequestParam(required = false) String name) {
        return ResponseEntity.ok().body(categoryService.getCategories(name));
    }

    @Operation(
            summary = "Buscar Categoria por ID",
            description = "Retorna os dados de uma categoria específica baseado no ID fornecido"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada (ID inexistente)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategory(id);
        return ResponseEntity.ok().body(category);
    }

    @Operation(summary = "Criar Categoria", description = "Criar uma nova Categoria")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> create(
            @RequestBody @Valid CategoryCreateDTO inputDTO,
            UriComponentsBuilder uriBuilder
    ) {
        CategoryDTO createdCategory =  categoryService.createCategory(inputDTO);

        // Cria a URI: localhost:8080/categories/{id}
        var uri = uriBuilder.path("/categories/{id}")
                .buildAndExpand(createdCategory.id())
                .toUri();

        // Retorna 201 Created com o Header Location e o corpo
        return ResponseEntity.created(uri).body(createdCategory);
    }

    @Operation(summary = "Atualizar Categoria", description = "Atualizar dados da Categoria")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada (ID inexistente)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody @Valid CategoryUpdateDTO inputDTO) {
        CategoryDTO updatedTask = categoryService.updateCategory(id, inputDTO);

        return ResponseEntity.ok().body(updatedTask);
    }

    @Operation(summary = "Deletar Categoria", description = "Deletar uma Categoria do Banco")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada (ID inexistente)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
