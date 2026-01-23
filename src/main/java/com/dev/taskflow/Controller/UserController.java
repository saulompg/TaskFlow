package com.dev.taskflow.Controller;

import com.dev.taskflow.DTOs.*;
import com.dev.taskflow.Service.UserService;
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
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    @Operation(
            summary = "Buscar Usuários",
            description = "Retorna todos os Usuários ou filtra por email se o filtro for utilizado"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Email não encontrado (apenas se o filtro for usado)"),
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false) String email) {
        return ResponseEntity.ok().body(userService.getUsers(email));
    }

    private final UserService userService;

    @Operation(
            summary = "Buscar Usuário por ID",
            description = "Retorna os dados de uma usuário específico baseado no ID fornecido"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado (ID inexistente)"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Criar Usuário", description = "Criar um novo Usuário")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping
    public ResponseEntity<UserDTO> create(
            @RequestBody @Valid UserCreateDTO inputDTO,
            UriComponentsBuilder uriBuilder
    ) {
        UserDTO createdUser = userService.createUser(inputDTO);

        // Cria a URI: localhost:8080/users/{id}
        var uri = uriBuilder.path("/users/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();

        // Retorna 201 Created com o Header Location e o corpo
        return ResponseEntity.created(uri).body(createdUser);
    }

    @Operation(summary = "Atualizar Usuário", description = "Atualizar dados do Usuário")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado (ID inexistente)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id, @RequestBody @Valid UserUpdateDTO inputDTO) {
        UserDTO updatedUser = userService.updateUser(id, inputDTO);
        return ResponseEntity.ok().body(updatedUser);
    }

    @Operation(summary = "Deletar Usuário", description = "Deletar um Usuário do Banco")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado (ID inexistente)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
