package com.trithon.trithon.service;

import com.trithon.trithon.domain.User;
import com.trithon.trithon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register
    public String registerUser(String username, String password, String email) {
        if(userRepository.findByUsername(username).isPresent()) {
            return "Username already exists!";
        }

        if(userRepository.findByEmail(email).isPresent()) {
            return "Email already exists!";
        }

        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, encodedPassword, email);
        userRepository.save(newUser);
        return "Registration successful!";
    }

    // Login
    public String loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()) {
            return "User not found!";
        }

        User user = userOptional.get();
        if(!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid password!";
        }

        // login success
        return "Login successful!";
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByName(String name) {
        return userRepository.findByUsername(name).get();
    }
}
