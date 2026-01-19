package com.dev.taskflow.Service;

import com.dev.taskflow.DTOs.UserCreateDTO;
import com.dev.taskflow.DTOs.UserDTO;
import com.dev.taskflow.DTOs.UserUpdateDTO;
import com.dev.taskflow.Entity.User;
import com.dev.taskflow.Repository.UserRepository;
import com.dev.taskflow.Service.Interface.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getUsers(String email) {
        if (email != null && !email.isEmpty()) {
            var user = userRepository.findByEmail(email);
            return user.map(value -> List.of(toDTO(value))).orElseGet(List::of);
        }

        return userRepository.findAll().stream()
            .map(this::toDTO).toList();
    }

    @Override
    public UserDTO getUserById(UUID id) {
        User user  = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
        return toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserCreateDTO dto) {
        User newUser = new User(dto.email(), dto.password(), dto.firstName(), dto.lastName());
        newUser = userRepository.save(newUser);
        return toDTO(newUser);
    }

    @Override
    @Transactional
    public UserDTO updateUser(UUID id, UserUpdateDTO dto) {
        User user  = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        userRepository.save(user);
        return toDTO(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
        userRepository.delete(user);
    }

    private UserDTO toDTO(User user) {
        return new  UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
