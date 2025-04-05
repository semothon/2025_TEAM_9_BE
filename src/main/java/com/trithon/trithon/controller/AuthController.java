package com.trithon.trithon.controller;

import com.trithon.trithon.domain.User;
import com.trithon.trithon.domain.dto.request.AuthRequestDto;
import com.trithon.trithon.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public String register(@RequestBody AuthRequestDto authRequestDto) {
        return authService.registerUser(authRequestDto.getUsername(), authRequestDto.getPassword(), authRequestDto.getEmail());
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDto authRequestDto) {
        return authService.loginUser(authRequestDto.getUsername(), authRequestDto.getPassword());
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return authService.getUsers();
    }

    @GetMapping("/users/{userId}/id")
    public User getUserById(@PathVariable String userId) {
        return authService.getUserById(userId);
    }

    @GetMapping("/users/{userName}/name")
    public User getUserByName(@PathVariable String userName) {
        return authService.getUserByName(userName);
    }
}
