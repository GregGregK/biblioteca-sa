package com.example.biblioteca.service;

import com.example.biblioteca.model.User;
import com.example.biblioteca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User create(User user) {
        User existUser = userRepository.findByUsername(user.getUsername());

        if (existUser != null) {
            throw new Error("User already exists!");
        }

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User createdUser = userRepository.save(user);

        return createdUser;
    }

}
