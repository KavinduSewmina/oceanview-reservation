package com.room.reservation.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.room.reservation.dto.LoginRequest;
import com.room.reservation.dto.LoginResponse;
import com.room.reservation.entity.User;
import com.room.reservation.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {

        // Find by username OR email
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new RuntimeException("Invalid username/email or password"));

        // Verify password using BCrypt
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid username/email or password");
        }

        // Simple token (for frontend sessionStorage)
        String token = UUID.randomUUID().toString();

        return new LoginResponse(token, user.getUsername(), user.getRole());
    }
}