package com.dev.taskflow.Controller;

import com.dev.taskflow.DTOs.*;

import com.dev.taskflow.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        var registerUser = userService.registerUser(dto);

        // Cria a URI: localhost:8080/users/{id}
        var uri = uriBuilder.path("/users/{id}")
                .buildAndExpand(registerUser.id())
                .toUri();

        // Retorna 201 Created com o Header Location e o corpo
        return ResponseEntity.created(uri).body(registerUser);
    }
}
