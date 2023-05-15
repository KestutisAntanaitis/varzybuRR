package com.example.varzybuRegistracija.service.impl;

import com.example.varzybuRegistracija.dto.UserDto;
import com.example.varzybuRegistracija.entity.Role;
import com.example.varzybuRegistracija.entity.User;
import com.example.varzybuRegistracija.repository.RoleRepository;
import com.example.varzybuRegistracija.repository.UserRepository;
import com.example.varzybuRegistracija.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new EntityNotFoundException("Could not find ROLE_USER in the database"));
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllByRole(String role) {
        List<User> users = userRepository.findAllByRoles_Name(role);
        return users.stream().map(user -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public Long getCountByRole(String role) {
        return userRepository.countAllByRoles_Name(role);
    }

    @Override
    public void deleteAllNonAdminUsers() {
        List<User> activeUsers = userRepository.findAllByRoles_Name("ROLE_USER");
        userRepository.deleteAll(activeUsers);
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
