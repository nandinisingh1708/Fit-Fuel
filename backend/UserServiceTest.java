package com.calorie.calculator.service;

import com.calorie.calculator.model.User;
import com.calorie.calculator.payload.request.SignupRequest;
import com.calorie.calculator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void registerUser_encodesPasswordAndSavesUser() {
        SignupRequest req = new SignupRequest();
        req.setUsername("testuser");
        req.setEmail("t@example.com");
        req.setPassword("plainpass");

        when(passwordEncoder.encode("plainpass")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User saved = userService.registerUser(req);

        assertNotNull(saved);
        assertEquals("encoded", saved.getPassword());
        assertEquals("testuser", saved.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
