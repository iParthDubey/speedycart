package com.speedycart.auth_service.service;

import com.speedycart.auth_service.dto.AuthRequest;
import com.speedycart.auth_service.dto.AuthResponse;
import com.speedycart.auth_service.dto.RegisterRequest;
import com.speedycart.auth_service.entity.Role;
import com.speedycart.auth_service.entity.User;
import com.speedycart.auth_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(RegisterRequest request){

        if(userRepository.findByUsername(request.getUserName()).isPresent()){
            throw new RuntimeException("User already exists with username: " + request.getUserName());
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        Set<Role> roles = request.getRoles() != null &&!request.getRoles().isEmpty()
                ? request.getRoles()
                : Set.of(Role.CUSTOMER);

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUserName())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        var savedUser = userRepository.save(user);
        return AuthResponse.builder().build();

    }


}
