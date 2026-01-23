package com.dev.taskflow.Service.Interface;

import com.dev.taskflow.DTOs.UserDTO;
import com.dev.taskflow.DTOs.UserCreateDTO;
import com.dev.taskflow.DTOs.UserRegisterDTO;
import com.dev.taskflow.DTOs.UserUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<UserDTO> getUsers(String email);
    UserDTO getUserById(UUID id);
    UserDTO createUser(UserCreateDTO dto);
    UserDTO registerUser(UserRegisterDTO dto);
    UserDTO updateUser(UUID id, UserUpdateDTO dto);
    void deleteUser(UUID id);
}
