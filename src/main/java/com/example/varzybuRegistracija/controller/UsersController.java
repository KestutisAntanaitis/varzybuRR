package com.example.varzybuRegistracija.controller;

import com.example.varzybuRegistracija.dto.UserDto;
import com.example.varzybuRegistracija.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllByRole("ROLE_USER");
        model.addAttribute("users", users);
        return "users";
    }
}
