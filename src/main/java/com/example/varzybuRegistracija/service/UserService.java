package com.example.varzybuRegistracija.service;

import com.example.varzybuRegistracija.dto.UserDto;
import com.example.varzybuRegistracija.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllByRole(String role);

    Long getCountByRole(String role);

    void deleteAllNonAdminUsers();
}
